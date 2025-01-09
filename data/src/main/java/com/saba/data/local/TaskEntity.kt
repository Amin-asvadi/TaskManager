package com.saba.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String?,
    val category: String?,
    val deadline: String?,
    val reminderTime: String?,
    val isReminderEnabled: Boolean,
    val markAsDone: Boolean,
    val expired:Boolean
)