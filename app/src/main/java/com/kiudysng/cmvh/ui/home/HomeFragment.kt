package com.kiudysng.cmvh.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter4.BaseQuickAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.kiudysng.cmvh.R
import com.kiudysng.cmvh.databinding.FragmentHomeBinding
import com.kiudysng.cmvh.db.entity.TaskEntity
import com.kiudysng.cmvh.ui.adapter.TaskAdapter
import com.kiudysng.cmvh.ui.task.AddTaskActivity
import com.kiudysng.cmvh.utils.RxBus

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null


    private val binding get() = _binding!!
    lateinit var adapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        init(homeViewModel)
        return root
    }

    fun init(viewModel: HomeViewModel) {
        adapter = TaskAdapter(viewModel)
        viewModel.getAllTaskList()
        binding.apply {
            rvTask.layoutManager = LinearLayoutManager(requireContext())
            rvTask.setHasFixedSize(true)
            rvTask.adapter = adapter
        }
        viewModel.allTaskListLiveData.observe(this) {
            adapter.submitList(it)
        }
        adapter.setOnItemLongClickListener(object :
            BaseQuickAdapter.OnItemLongClickListener<TaskEntity> {
            override fun onLongClick(
                adapter: BaseQuickAdapter<TaskEntity, *>,
                view: View,
                position: Int
            ): Boolean {
                Log.e("xxxlala", "长按 ${adapter.getItem(position)?.taskInfo}")
                adapter.getItem(position)?.let { task ->
                    val dialog = BottomSheetDialog(requireContext());
                    val view = layoutInflater.inflate(R.layout.dialog_task_long_click_layout, null);
                    val taskInfo = view.findViewById<TextView>(R.id.tv_task_info)
                    taskInfo.text = task.taskInfo
                    val taskEdit = view.findViewById<TextView>(R.id.tv_edit_todo)
                    taskEdit.setOnClickListener {
                        val intent: Intent =
                            Intent(requireContext(), AddTaskActivity::class.java).apply {
                                putExtra("taskInfo", task.taskInfo)
                                putExtra("taskCreateTime", task.createTime)
                                startActivity(this)
                                dialog.dismiss()
                            }
                    }
                    val taskDelete = view.findViewById<TextView>(R.id.tv_delete_todo)
                    taskDelete.setOnClickListener {
                        viewModel.deleteItemTask(task)
                        dialog.dismiss()
                    }
                    dialog.setContentView(view);
                    dialog.show();
                }
                return false
            }

        })
        RxBus.observerEvent(this) {
            when (it) {
                "Confirm",
                "addTaskSuccess" -> {
                    viewModel.getAllTaskList()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}