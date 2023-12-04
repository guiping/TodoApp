package com.kiudysng.cmvh.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tab_task")
data class TaskEntity(@PrimaryKey val idKey: Int, val taskInfo: String, val confirm:Int,val createTime: Long)
