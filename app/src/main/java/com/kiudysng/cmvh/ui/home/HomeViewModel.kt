package com.kiudysng.cmvh.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiudysng.cmvh.TaskApplication
import com.kiudysng.cmvh.db.DbDatabase
import com.kiudysng.cmvh.db.dao.TaskDao
import com.kiudysng.cmvh.db.entity.TaskEntity
import com.kiudysng.cmvh.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel() {

    val allTaskListLiveData = MutableLiveData<List<TaskEntity>>()
    private val taskDao: TaskDao by lazy {
        DbDatabase.buildDatabase(TaskApplication.appContext).taskDao()
    }

    fun getAllTaskList() { //获取所有数据
        viewModelScope.launch(Dispatchers.IO) {
            val tempAllTaskData = taskDao.getAllTaskData()
            allTaskListLiveData.postValue(tempAllTaskData)
        }
    }

    fun deleteItemTask(taskEntity: TaskEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val tempAllTaskData = taskDao.deleteItemTask(taskEntity)
            Log.e("xxLog", "tempAllTaskData  = $tempAllTaskData")
            if (tempAllTaskData != 0) {
                getAllTaskList()
            }
        }
    }

    override fun updateItemStatue(confirm: Int, createTime: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val tempLong = taskDao.updateItemTask(confirm, createTime)
            Log.e("xxLog", "更新数据confirm = $confirm --- createTime = $createTime")
            if (tempLong != 0) {
                getAllTaskList()
            }
        }
    }
}