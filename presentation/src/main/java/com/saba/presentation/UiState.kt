package com.saba.presentation

import com.saba.data.model.local.TaskEntity

data class UiState(
    val dialogAddTask: Boolean = false,
    val bottomSheetView: Boolean = false,
    val tasks: List<TaskEntity> = emptyList(),
    val singleTask: TaskEntity? =null,
    val title: String = "",
    val description: String = "",
    val activeCategory: String = "همه",
    val bottomSheeTitle:String ="",
    val taskId:Int = -1,
    val bottomSheetDescription:String ="",
    val isReminderEnabled: Boolean = false,
    val updateComplete: Boolean = false,
    val timePicker:String?=null,
    val datePicker:String?=null,
    val taskCategory:String="همه"
)