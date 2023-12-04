package com.kiudysng.cmvh.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kiudysng.cmvh.db.entity.TaskEntity

@Dao
interface TaskDao {
    @Insert
    fun addTask(taskEntity: TaskEntity):Long

    @Query("SELECT * FROM tab_task")
    fun getAllTaskData(): List<TaskEntity>

    @Query("SELECT * FROM tab_task WHERE confirm!=1")
    fun getAllNotConfirmTaskData(): List<TaskEntity>

    @Query("UPDATE tab_task SET confirm = 1 WHERE idKey = :taskId")
    fun updateItemTask(taskId: Int)
}