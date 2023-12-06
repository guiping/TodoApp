package com.kiudysng.cmvh.ui.task

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.kiudysng.cmvh.databinding.FragmentAddTodoBinding
import com.kiudysng.cmvh.db.entity.TaskEntity
import com.kiudysng.cmvh.ext.makeLongSnackBar
import kotlinx.coroutines.GlobalScope

class AddTaskActivity : AppCompatActivity() {

    private var _binding: FragmentAddTodoBinding? = null


    private val binding get() = _binding!!
    private var taskInfo: String? = null
    private var taskCreateTime: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 在活动的onCreate方法中
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        supportActionBar?.hide() // 隐藏默认的操作栏

        _binding = FragmentAddTodoBinding.inflate(layoutInflater)
        val root: View = binding.root
        setContentView(root)
        val bundle = intent.extras
        if (bundle != null) {
            taskInfo = bundle.getString("taskInfo")
            taskCreateTime = bundle.getLong("taskCreateTime")
        }
        val homeViewModel = ViewModelProvider(this@AddTaskActivity)[TaskViewModel::class.java]
        initView(homeViewModel)
        initData(homeViewModel)
    }

    private fun initView(vm: TaskViewModel) {
        binding.run {
            taskInfo?.apply {
                todoInput.setText(taskInfo)
            }

            saveTodo.setOnClickListener {   //保存
                val todoInputInfo = todoInput.text.toString()
                if (todoInputInfo.isNotEmpty()) {
                    if (taskInfo != null) {
                        vm.updateItemTask(todoInputInfo, taskCreateTime)
                    } else {
                        val tempTaskEntity =
                            TaskEntity(0, todoInputInfo, 0, System.currentTimeMillis())
                        vm.addNewTask(tempTaskEntity)
                    }
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

                    finish()
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}