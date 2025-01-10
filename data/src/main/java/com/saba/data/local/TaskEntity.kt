package com.saba.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String?,
    val category: String? = null,
    val reminderTime: String? = null,
    val isReminderEnabled: Boolean = false,
    val markAsDone: Boolean = false,
)