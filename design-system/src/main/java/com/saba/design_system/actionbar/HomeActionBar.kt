package com.saba.design_system.actionbar

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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.saba.base_android.uiles.clickableSingle
import com.saba.base_android.uiles.multipleEventsCutter
import kotlinx.coroutines.delay
import com.saba.common_ui_resources.R
import com.saba.design_system.text.CustomText
import com.saba.design_system.textfiled.CustomOutlineTextFiled
import com.saba.design_system.theme.Gray_300
import com.saba.design_system.theme.Gray_600
import com.saba.design_system.theme.Gray_900

@Composable
fun HomeActionBar(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onThemeClick: () -> Unit
) {
Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = modifier.weight(1f)) {
            Icon(
                painter = painterResource(id = R.drawable.sunny_24),
                contentDescription = "notification",
                modifier
                    .size(30.dp)
                    .clickable {
                        onThemeClick()
                    },
                tint = MaterialTheme.colorScheme.surfaceContainerHighest
            )
        }
        CustomOutlineTextFiled(modifier = modifier
            .weight(8f),
            value = value,
            height = 38.dp,
            singleLine = true,
            placeholder = {
                CustomText(
                    text = "جستجو",
                    color = Gray_600
                )
            },
            onValueChange = {
                onValueChange(it)
            }
        )

    }
    Box(modifier = modifier.fillMaxWidth().background(Gray_300).height(1.dp))
}


}