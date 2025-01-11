package com.saba.design_system.btn

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OutlinedButtonWithIcon(
    modifier: Modifier = Modifier,
    text: String = "",
    enabled: Boolean = true,
    fontSize: Int = 13,
    border: BorderStroke = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceContainerLow),
    buttonColors: ButtonColors = ButtonDefaults.outlinedButtonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.background
    ),
    textColor: Color = MaterialTheme.colorScheme.surface,
    iconColor: Color? = null,
    fontWeight: FontWeight = FontWeight.Normal,
    leftIcon: Int? = null,
    rightIcon: Int? = null,
    isLoading: Boolean = false,
    imgSize: Int = 24,
    cornerRadius: Dp = 12.dp,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = { if (!isLoading) onClick() },
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(cornerRadius),
        border = if (enabled) border else ButtonDefaults.outlinedButtonBorder(enabled),
        colors = buttonColors,
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // Show loading indicator
            if (isLoading) {
                CircularProgressIndicator(
                    strokeWidth = 3.dp,
                    color = textColor,
                    modifier = Modifier.size(20.dp)
                )
            } else {
                // Left icon
                leftIcon?.let {
                    Image(
                        painter = painterResource(id = it),
                        contentDescription = null,
                        modifier = Modifier.size(imgSize.dp),
                        colorFilter = iconColor?.let { ColorFilter.tint(it) }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }

                // Text
                if (text.isNotEmpty()) {
                    Text(
                        text = text,
                        fontSize = fontSize.sp,
                        color = textColor,
                        fontWeight = fontWeight
                    )
                }

                // Right icon
                rightIcon?.let {
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painter = painterResource(id = it),
                        contentDescription = null,
                        modifier = Modifier.size(imgSize.dp),
                        colorFilter = iconColor?.let { ColorFilter.tint(it) }
                    )
                }
            }
        }
    }
}
