package com.kiudysng.cmvh.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kiudysng.cmvh.databinding.FragmentGalleryBinding
import com.kiudysng.cmvh.ui.adapter.TaskAdapter
import com.kiudysng.cmvh.ui.adapter.TaskHistoryAdapter
import com.kiudysng.cmvh.utils.RxBus

class TaskHistoryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    lateinit var adapter: TaskAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val taskHistoryViewModel = ViewModelProvider(this)[TaskHistoryViewModel::class.java]

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        init(taskHistoryViewModel)
        return root
    }
    private fun init(vm: TaskHistoryViewModel) {
        adapter = TaskAdapter(vm)
        binding.apply {
            rvTaskHistory.layoutManager = LinearLayoutManager(requireContext())
            rvTaskHistory.adapter = adapter
        }
        vm.apply {
            getAllTaskList()
            allTaskListLiveData.observe(this@TaskHistoryFragment) {
                adapter.submitList(it)
            }
        }
        RxBus.observerEvent(this) {
            if ("Confirm" == it) {
                vm.getAllTaskList()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}