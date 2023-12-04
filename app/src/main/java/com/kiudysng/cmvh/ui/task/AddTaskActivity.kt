package com.kiudysng.cmvh.ui.task

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.kiudysng.cmvh.databinding.FragmentAddTodoBinding
import com.kiudysng.cmvh.db.entity.TaskEntity
import kotlinx.coroutines.GlobalScope

class AddTaskActivity : AppCompatActivity() {

    private var _binding: FragmentAddTodoBinding? = null


    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = FragmentAddTodoBinding.inflate(layoutInflater)
        val root: View = binding.root
        setContentView(root)

        val homeViewModel = ViewModelProvider(this@AddTaskActivity).get(TaskViewModel::class.java)
        initView(homeViewModel)
        initData(homeViewModel)
    }

    private fun initView(vm: TaskViewModel) {
        binding.run {
            saveTodo.setOnClickListener {   //保存
                val todoInputInfo = todoInput.text.toString()
                if (todoInputInfo.isNotEmpty()) {
                    val taskEntity = TaskEntity(0, todoInputInfo, 0, System.currentTimeMillis())
                    vm.addNewTask(taskEntity)
                }
            }
            todoInput.addTextChangedListener(onTextChanged = { text, _, _, _ ->
                vm.onTodoTextChange(text)
            })

        }
    }

    private fun initData(vm: TaskViewModel) {
        vm.apply {
            viewStateObservable.observe(this@AddTaskActivity) {
                binding.saveTodo.isEnabled = it
            }
            addTaskSuccess.observe(this@AddTaskActivity) {
                if (it == "success") {
                    finish()  //shuax UI
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}