package com.saba.design_system.textfiled

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.saba.design_system.theme.Gray_300
import com.saba.design_system.theme.Gray_600
import com.saba.design_system.theme.Gray_900
import com.saba.design_system.theme.White

@Composable
fun CustomBasicTextField(
    modifier: Modifier = Modifier,
    valueTextFiled: String ="",
    readOnly: Boolean = false,
    leadingIcon: (@Composable () -> Unit)? = null,
    placeholderText: String? = "عنوان",
    height:Dp = 38.dp,
    singleLine: Boolean = false,
    maxLength: Int = Int.MAX_VALUE,
    fontSize: TextUnit = MaterialTheme.typography.bodySmall.fontSize,
    textDirection: TextDirection = TextDirection.Rtl,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    roundedShape: Int = 8,
    focused: (Boolean) -> Unit = {},
    onValueChange: (String) -> Unit
) {

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Card(
                modifier = modifier.fillMaxWidth().height(height)
                    .padding(0.dp),
                shape = RoundedCornerShape(roundedShape.dp),
                colors = CardDefaults.cardColors(
                    containerColor = White,

                    ),
                border = BorderStroke(
                    width = 1.dp,
                    Gray_300
                )
            ) {

                BasicTextField(
                    modifier = modifier
                        .padding(horizontal = 16.dp)
                        .height(height)
                        .onFocusChanged { focusState ->
                            focused(focusState.isFocused)
                        }
                        .background(
                            White,
                            MaterialTheme.shapes.small,
                        ),
                    value = valueTextFiled,
                    onValueChange = {

                        if (it.length <= maxLength) {
                            onValueChange(it)
                           // changeValue = it
                        }
                    },
                    readOnly = readOnly,
                    singleLine = singleLine,
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                    textStyle = LocalTextStyle.current.copy(
                        color = Gray_900,
                        fontSize = fontSize,
                        textDirection = textDirection,
                    ),
                    keyboardOptions = keyboardOptions,
                    keyboardActions = keyboardActions,
                    decorationBox = { innerTextField ->

                        Row(
                            modifier.fillMaxHeight().padding(top = if (singleLine) 0.dp else 10.dp),
                            verticalAlignment = if (singleLine) Alignment.CenterVertically else Alignment.Top
                        ) {
                            Box(Modifier.weight(1f)) {
                                if (valueTextFiled.isEmpty()) placeholderText?.let {
                                    Text(
                                        it,
                                        style = LocalTextStyle.current.copy(
                                            color = Gray_600,
                                            fontSize = fontSize,
                                            textDirection = textDirection // Use the provided textDirection
                                        )
                                    )
                                }
                                innerTextField()
                            }
                            if (leadingIcon != null) leadingIcon()
                        }
                    }
                )
            }
    }
}

