package com.saba.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.saba.design_system.text.CustomText
import com.saba.design_system.theme.Gray_300

@Composable
fun RowComponent(modifier: Modifier = Modifier, icon: Int, title: String, value: String,onClickEvent:()->Unit) {
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 0.dp)
                .clickable { onClickEvent() },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = modifier
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(modifier = modifier.size(50.dp), onClick = {}) {
                    Icon(painter = painterResource(icon), contentDescription = "Icon")
                }
                CustomText(title, fontWeight = FontWeight.Bold, fontSize = 13)
            }
            CustomText(value, fontWeight = FontWeight.Bold, fontSize = 13)
        }
        Box(modifier=modifier.fillMaxWidth().height(1.dp).background(Gray_300))
    }
}