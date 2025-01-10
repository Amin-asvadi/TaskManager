package com.saba.design_system.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.saba.data.local.TaskEntity
import com.saba.design_system.textfiled.CustomBasicTextField

@Composable
fun EditTaskBottomSheetView(
    modifier: Modifier = Modifier,
    item: TaskEntity?,
    titleValue: String,
    descriptionValue: String,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.7f).background(MaterialTheme.colorScheme.background)
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
            height = 200.dp
        )
    }
}