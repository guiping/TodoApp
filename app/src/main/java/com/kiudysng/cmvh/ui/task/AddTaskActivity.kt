package com.kiudysng.cmvh.ui.task

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
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
        // 隐藏导航栏
//        window.decorView.systemUiVisibility = (
//                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
//                        View.SYSTEM_UI_FLAG_FULLSCREEN
//                )
        // 在活动的onCreate方法中
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        supportActionBar?.hide() // 隐藏默认的操作栏

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            val window = window
//            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
//                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//            window.statusBarColor = Color.TRANSPARENT
//        }
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