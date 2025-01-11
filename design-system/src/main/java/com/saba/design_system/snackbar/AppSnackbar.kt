package com.saba.design_system.snackbar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.saba.design_system.text.CustomText

@Composable
fun AppSnackbar(
    modifier: Modifier,
    message: String = "",
    messageColor: Color = MaterialTheme.colorScheme.surfaceContainerHighest,
    backgroundColor: Color = MaterialTheme.colorScheme.inversePrimary.copy(alpha = 0.30f),
    borderColor: Color = MaterialTheme.colorScheme.inversePrimary,
    btnTxtColor: Color = MaterialTheme.colorScheme.inversePrimary,
    messageFontSize: Int = 12,
    btnFontSize: Int = 12,
    icon: @Composable (() -> Unit),
    isVisibleBtn: Boolean = false,
    btnTxt: String = "",
    btnAction: () -> Unit
) {
    var expand by remember { mutableStateOf(false) }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .heightIn(min= 54.dp)
            .background(color = backgroundColor, shape = RoundedCornerShape(12.dp))
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 10.dp).clickable {
                expand =!expand
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        icon()
        Row {
            Spacer(modifier = Modifier.width(8.dp))
            CustomText(
                text = message,
                fontSize = messageFontSize,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.fillMaxWidth(0.7f).padding(vertical = 8.dp),
                color = messageColor,
                maxLines =if (expand) 3 else 1
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        CustomText(
                text = "متوجه شدم",
                fontSize = btnFontSize,
                fontWeight = FontWeight.Normal,
                color = btnTxtColor,
                lineHeight = 26.sp,
                modifier = Modifier.clickable { btnAction() },
            )
    }
}