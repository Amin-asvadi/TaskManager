package com.saba.design_system.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.saba.design_system.btn.OutlinedButtonWithIcon
import com.saba.design_system.textfiled.CustomBasicTextField
import com.saba.design_system.theme.Gray_900
import com.saba.design_system.theme.Primary
import com.saba.design_system.theme.White

@Composable
fun AddDialog(
    modifier: Modifier = Modifier,
    isDark: Boolean,
    showDialog: Boolean,
    titleValue: String,
    descriptionValue: String,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    dissmissRequired: () -> Unit,
    onCreateClick: () -> Unit
) {
    if (showDialog) {
        Dialog(onDismissRequest = dissmissRequired) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = if (isDark) Gray_900 else White),
                    modifier = modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.4f),
                    border = BorderStroke(width = 1.dp, color = Primary)
                ) {
                    Column(
                        modifier = modifier.padding(16.dp),
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
                            height = 200.dp
                        )
                        OutlinedButtonWithIcon(
                            modifier = modifier
                                .fillMaxWidth()
                                .height(45.dp),
                            textColor = White,
                            fontWeight = FontWeight.Bold,
                            text = "ایجاد یادآور",
                            border = BorderStroke(
                                width = 1.dp,
                                color = Primary,
                            )
                        ) {
                            onCreateClick()
                        }
                    }
                }
        }
    }
}