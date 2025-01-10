package com.saba.base_android.uiles

import android.annotation.SuppressLint
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce

interface MultipleEventsCutterManager {
    fun processEvent(event: () -> Unit)
}
@SuppressLint("ModifierFactoryUnreferencedReceiver")
@Composable
fun Modifier.feedBackClickable(
    hapticFeedbackType: HapticFeedback = LocalHapticFeedback.current,
    onClick: () -> Unit,
) = clickableSingle(
    enabled = true,
    onClick = {
        hapticFeedbackType.performHapticFeedback(HapticFeedbackType.LongPress)
        onClick()
    }
)
@OptIn(FlowPreview::class)
@Composable
fun <T>multipleEventsCutter(
    debounce:Long = 2000,
    content: @Composable (MultipleEventsCutterManager) -> T
) : T {
    val debounceState = remember {
        MutableSharedFlow<() -> Unit>(
            replay = 0,
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
    }

    val result = content(
        object : MultipleEventsCutterManager {
            override fun processEvent(event: () -> Unit) {
                debounceState.tryEmit(event)
            }
        }
    )

    LaunchedEffect(true) {
        debounceState
            .debounce(debounce)
            .collect { onClick ->
                onClick.invoke()
            }
    }
    return result
}
@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.clickableSingle(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "clickable"
        properties["enabled"] = enabled
        properties["onClickLabel"] = onClickLabel
        properties["role"] = role
        properties["onClick"] = onClick
    }
) {
    multipleEventsCutter { manager ->
        Modifier.clickable(
            enabled = enabled,
            onClickLabel = onClickLabel,
            onClick = { manager.processEvent { onClick() } },
            role = role,
            indication = LocalIndication.current,
            interactionSource = remember { MutableInteractionSource() }
        )
    }
}