package com.saba.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.saba.data.model.local.CategoryEntity
import com.saba.data.model.local.RemoteModelItemEntity
import com.saba.data.model.local.TaskEntity

@Database(entities = [TaskEntity::class, CategoryEntity::class,RemoteModelItemEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): DatabaseDao
}