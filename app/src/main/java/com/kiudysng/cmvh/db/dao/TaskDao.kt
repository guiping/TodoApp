package com.kiudysng.cmvh.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.kiudysng.cmvh.db.entity.TaskEntity

@Dao
interface TaskDao {
    @Insert
    fun addTask(taskEntity: TaskEntity): Long

    @Query("SELECT * FROM tab_task ORDER BY  confirm ASC ,createTime DESC")
    fun getAllTaskData(): List<TaskEntity>

    @Query("SELECT * FROM tab_task WHERE confirm = 1 ")
    fun getAllNotConfirmTaskData(): List<TaskEntity>

    @Query("UPDATE tab_task SET confirm = :confirm WHERE createTime = :createTime")
    fun updateItemTask(confirm: Int, createTime: Long): Int
    @Query("UPDATE tab_task SET taskInfo = :info WHERE createTime = :createTime")
    fun updateItemTask(info: String, createTime: Long): Int
    @Delete
    fun deleteItemTask(taskEntity: TaskEntity):Int
}