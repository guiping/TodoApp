package com.kiudysng.cmvh.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tab_task")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val idKey: Int,
    val taskInfo: String,
    val confirm: Int = 0,
    val createTime: Long,
    val isDelete: Int = 0
) : java.io.Serializable
