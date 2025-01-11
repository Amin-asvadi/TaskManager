package com.saba.design_system.bottomsheet


import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalUseFallbackRippleImplementation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.saba.data.model.local.CategoryEntity
import com.saba.design_system.btn.OutlinedButtonWithIcon
import com.saba.design_system.component.RowComponent
import com.saba.design_system.text.CustomText
import com.saba.design_system.textfiled.CustomBasicTextField
import com.saba.design_system.theme.Gray_400
import com.saba.design_system.theme.Gray_900
import com.saba.design_system.theme.Primary
import com.saba.design_system.theme.White
import com.saba.design_system.time.TimePickerDialog
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskBottomSheetView(
    modifier: Modifier = Modifier,
    categories: List<CategoryEntity>,
    titleValue: String,
    isDark: Boolean,
    time: String?,
    date: String?,
    descriptionValue: String,
    taskCategory: String,
    onDateConfirm: (String) -> Unit,
    onTimeConfirm: (String) -> Unit,
    selectedCategory: (String) -> Unit,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    updateTask: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState()
    var showDatePicker by remember { mutableStateOf(false) }

    val timePickerState = rememberTimePickerState()
    var showTimePicker by remember { mutableStateOf(false) }

    if (showDatePicker) {
        CompositionLocalProvider(
            LocalUseFallbackRippleImplementation provides true,
            LocalLayoutDirection provides LayoutDirection.Ltr,
        ) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val date = datePickerState.selectedDateMillis?.let { millis ->
                                // تبدیل تاریخ به فرمت قابل خواندن
                                val formatter = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
                                formatter.format(Date(millis))
                            } ?: "تاریخ انتخاب نشده"
                            onDateConfirm(date)
                            showDatePicker = false
                        }
                    ) { CustomText("انتخاب", color = Gray_900) }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showDatePicker = false
                        }
                    ) { CustomText("کنسل", color = Gray_900) }
                },
            )
            {
                DatePicker(state = datePickerState)
            }
        }

    }

// time picker component
    if (showTimePicker) {
        TimePickerDialog(
            onDismissRequest = { showTimePicker = false },
            isDark = isDark,
            confirmButton = {
                TextButton(
                    onClick = {
                        val time = timePickerState.let { time ->
                            val formattedTime = String.format(
                                "%02d:%02d",
                                time.hour,
                                time.minute
                            )
                            formattedTime
                        }
                        onTimeConfirm(time)
                        showTimePicker = false
                    }
                ) { CustomText("انتخاب", color = Gray_900, fontWeight = FontWeight.Bold) }
            },
            dismissButton = { CustomText("کنسل", color = Gray_900, fontWeight = FontWeight.Bold) }
        )
        {
            TimePicker(state = timePickerState)
        }
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .offset(y = 30.dp)
            .background(MaterialTheme.colorScheme.background)


    )
    Box(
        modifier = modifier
            .padding(top = 2.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = modifier
                .padding(top = 2.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth()
                .fillMaxHeight(0.99f)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = modifier
                        .width(40.dp)
                        .height(4.dp)
                        .clip(CircleShape)
                        .background(Gray_400)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.background),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CustomBasicTextField(valueTextFiled = titleValue, onValueChange = {
                    onTitleChange(it)
                }, singleLine = true)
                CustomBasicTextField(
                    valueTextFiled = descriptionValue,
                    placeholderText = "یاداشت",
                    onValueChange = {
                        onDescriptionChange(it)
                    },
                    height = 150.dp
                )
                // Dropdown Menu
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(horizontal = 16.dp)
                        .clickable {
                            expanded = true
                        },
                ) {
                    CustomText(
                        taskCategory,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14,
                        modifier = modifier.align(Alignment.CenterStart)
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Arrow",
                        modifier = modifier.align(Alignment.CenterEnd)
                    )

                    DropdownMenu(
                        modifier = modifier
                            .fillMaxWidth(0.9f)
                            .height(200.dp),
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        containerColor = MaterialTheme.colorScheme.background
                    ) {
                        categories.forEach { ca ->
                            Row(
                                modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedCategory(ca.category)
                                        expanded = false
                                    }
                                    .height(30.dp)
                                    .padding(horizontal = 12.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (taskCategory.equals(ca)) Primary else MaterialTheme.colorScheme.surfaceContainerLow)
                                    .padding(horizontal = 12.dp),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CustomText(
                                    text = ca.category,
                                    color = MaterialTheme.colorScheme.surfaceContainerHighest
                                )
                            }
                            Spacer(modifier = modifier.height(8.dp))
                            /* DropdownMenuItem(onClick = {
                                 selectedCategory(ca.category)
                                 expanded = false
                             }) {
                                 CustomText(text = ca.category)
                             }*/
                        }
                    }
                }

                RowComponent(
                    icon = com.saba.common_ui_resources.R.drawable.baseline_calendar_month_24,
                    title = "روز یادآوری",
                    value = date ?: "انتخاب نشده",
                    onClickEvent = {
                        showDatePicker = true
                    }
                )
                RowComponent(
                    icon = com.saba.common_ui_resources.R.drawable.baseline_access_time_filled_24,
                    title = "زمان یادآوری",
                    value = time ?: "انتخاب نشده",
                    onClickEvent = {
                        showTimePicker = true
                    }
                )
                OutlinedButtonWithIcon(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(45.dp),
                    textColor = White,
                    fontWeight = FontWeight.Bold,
                    text = "بروزرسانی",
                    border = BorderStroke(
                        width = 1.dp,
                        color = Primary,
                    )
                ) {
                    updateTask()
                }

            }
        }

    }


    /**/
}