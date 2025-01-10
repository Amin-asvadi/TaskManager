package com.saba.design_system.item

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.saba.design_system.text.CustomText
import com.saba.design_system.theme.Gray_600
import com.saba.design_system.theme.SECONDERY_ACTIVE

/**
 * Composable that represents a single email message card.
 *
 * @param emailMessage The email message to display.
 */
@Composable
fun TaskCard(emailMessage: String, value: Boolean, onCheckdClick: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .height(60.dp)
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),

        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomText(
            emailMessage,
            fontWeight = FontWeight.Bold
        )
        Switch(
            checked = value,
            onCheckedChange = {
                onCheckdClick(it)
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = SECONDERY_ACTIVE,
                uncheckedThumbColor = MaterialTheme.colorScheme.background,
                uncheckedTrackColor = MaterialTheme.colorScheme.surfaceContainerLow,
                uncheckedBorderColor = MaterialTheme.colorScheme.surfaceContainerLowest
            )
        )
    }
}
