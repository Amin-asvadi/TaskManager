package com.saba.design_system.item

import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue.EndToStart
import androidx.compose.material3.SwipeToDismissBoxValue.Settled
import androidx.compose.material3.SwipeToDismissBoxValue.StartToEnd
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.saba.design_system.theme.Gray_600
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Composable representing an email item with swipe-to-dismiss functionality.
 *
 * @param emailMessage The email message to display.
 * @param modifier optional parameters to add extra behaviour to EmailItem.
 * @param onRemove Callback invoked when the email item is dismissed.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskItem(
    emailMessage: String,
    modifier: Modifier = Modifier,
    onItemClick:()->Unit,
    index: Int,
    onRemove: (String) -> Unit
) {
    val context = LocalContext.current
    val offsetX = remember { Animatable(0f) } // برای انیمیشن کشیدن
    val scope = rememberCoroutineScope()

    val currentItem by rememberUpdatedState(emailMessage)
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            when (it) {
                StartToEnd -> {
                    onRemove(currentItem)
                }

                EndToStart -> {
                    onItemClick()
                    return@rememberSwipeToDismissBoxState false
                }

                Settled -> return@rememberSwipeToDismissBoxState false
            }
            return@rememberSwipeToDismissBoxState true
        },
        // positional threshold of 25%
        positionalThreshold = { it * .25f }
    )

    // افکت کشیدن به سمت راست و بازگشت
    LaunchedEffect(Unit) {
        if (index == 0) {
            scope.launch {
                offsetX.animateTo(
                    targetValue = 40f, // مقدار حرکت به سمت راست
                    animationSpec = tween(durationMillis = 300) // مدت زمان حرکت به سمت راست
                )
                delay(1500) // توقف به مدت یک ثانیه
                offsetX.animateTo(
                    targetValue = 0f, // بازگشت به جای اصلی
                    animationSpec = tween(durationMillis = 300)
                )
            }
        }

    }

    SwipeToDismissBox(
        state = dismissState,
        modifier = modifier
            .padding(horizontal = 16.dp)
            .clip(MaterialTheme.shapes.small)
            .border(width = 1.dp, color = Gray_600, shape = MaterialTheme.shapes.small)
            .offset { IntOffset(offsetX.value.toInt(), 0) }
            .clickable {
                onItemClick()
            },
        backgroundContent = { DismissBackground(dismissState) },
        content = {
            TaskCard(emailMessage, value = true, onCheckdClick = {})
        }
    )
}
