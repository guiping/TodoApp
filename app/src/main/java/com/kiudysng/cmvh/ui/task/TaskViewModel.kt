package com.kiudysng.cmvh.ui.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiudysng.cmvh.TaskApplication
import com.kiudysng.cmvh.db.DbDatabase
import com.kiudysng.cmvh.db.dao.TaskDao
import com.kiudysng.cmvh.db.entity.TaskEntity
import com.kiudysng.cmvh.utils.RxBus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {

    val viewStateObservable: MutableLiveData<Boolean> = MutableLiveData()
    val addTaskSuccess: MutableLiveData<String> = MutableLiveData()
    val dao: TaskDao by lazy {
        DbDatabase.buildDatabase(TaskApplication.appContext).taskDao()
    }

    fun onTodoTextChange(text: CharSequence?) {
        viewStateObservable.value = text?.isNotEmpty() == true
    }

    fun addNewTask(taskEntity: TaskEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val tempLong = dao.addTask(taskEntity)
            if (tempLong != 0L) {
                addTaskSuccess.postValue("success")
                RxBus.post("addTaskSuccess")
            }
        }
    }

    fun updateItemTask(taskInfo: String, createTime: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val tempLong = dao.updateItemTask(taskInfo,createTime)
            if (tempLong != 0) {
                addTaskSuccess.postValue("success")
                RxBus.post("addTaskSuccess")
            }
        }
    }
}