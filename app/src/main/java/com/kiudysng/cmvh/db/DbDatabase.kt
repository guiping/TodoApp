package com.kiudysng.cmvh.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kiudysng.cmvh.db.dao.TaskDao
import com.kiudysng.cmvh.db.entity.TaskEntity

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
abstract class DbDatabase : RoomDatabase() {
    abstract fun taskDao():TaskDao
    companion object {
        private const val dbName = "taskDB.db"
        fun buildDatabase(context: Context): DbDatabase {
            return Room.databaseBuilder(context.applicationContext, DbDatabase::class.java, dbName)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}