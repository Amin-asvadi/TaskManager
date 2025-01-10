package com.saba.design_system.animations

import android.graphics.Path
import android.view.MotionEvent
import androidx.annotation.FloatRange
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.util.fastForEach
import kotlin.math.hypot


@Composable
fun CircularReveal(
    expanded: Boolean,
    modifier: Modifier = Modifier,
    animationSpec: FiniteAnimationSpec<Float> = tween(),
    content: @Composable (Boolean) -> Unit,
) {
    val transition = updateTransition(expanded, label = "Circular reveal")
    transition.CircularReveal(modifier, animationSpec, content = content)
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun Transition<Boolean>.CircularReveal(
    modifier: Modifier = Modifier,
    animationSpec: FiniteAnimationSpec<Float> = tween(),
    content: @Composable (targetState: Boolean) -> Unit,
) {
    var offset: Offset? by remember { mutableStateOf(null) }
    val currentlyVisible = remember { mutableStateListOf<Boolean>().apply { add(false) } }
    val contentMap = remember {
        mutableMapOf<Boolean, @Composable () -> Unit>()
    }
    if (currentState == targetState) {
        // If not animating, just display the current state
        if (currentlyVisible.size != 1 || currentlyVisible[0] != targetState) {
            contentMap.clear()
        }
    }
    if (!contentMap.contains(targetState)) {
        // Replace target with the same key if any
        val replacementId = currentlyVisible.indexOfFirst {
            it == targetState
        }
        if (replacementId == -1) {
            currentlyVisible.add(targetState)
        } else {
            currentlyVisible[replacementId] = targetState
        }
        contentMap.clear()
        currentlyVisible.fastForEach { stateForContent ->
            contentMap[stateForContent] = {
                val progress by animateFloat(
                    label = "Progress",
                    transitionSpec = { animationSpec }
                ) {
                    val targetedContent = stateForContent != currentlyVisible.last() || it == stateForContent
                    if (targetedContent) 1f else 0f
                }

             /*   val focusRequester = remember { FocusRequester() }
                LaunchedEffect(targetState) {
                    focusRequester.requestFocus()
                }*/
                Box(
                    modifier = Modifier
                        .focusable()
                      //  .focusRequester(focusRequester)
                        .circularReveal(progress = progress, offset = offset)
                ) {
                    content(stateForContent)
                }
            }
        }
    }
    Box(
        modifier = modifier.pointerInteropFilter {
            if (it.action == MotionEvent.ACTION_DOWN) {
                if (!started) offset = Offset(it.x, it.y)
            }
            started
        }
    ) {
        currentlyVisible.fastForEach {
            key(it) {
                contentMap[it]?.invoke()
            }
        }
    }
}

private val Transition<Boolean>.started get() =
    currentState != targetState || isRunning

private fun Modifier.circularReveal(
    @FloatRange(from = 0.0, to = 1.0) progress: Float,
    offset: Offset? = null,
) = clip(CircularRevealShape(progress, offset))

private class CircularRevealShape(
    @FloatRange(from = 0.0, to = 1.0) private val progress: Float,
    private val offset: Offset? = null,
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        return Outline.Generic(Path().apply {
            addCircle(
                offset?.x ?: (size.width / 2f),
                offset?.y ?: (size.height / 2f),
                longestDistanceToACorner(size, offset) * progress,
                Path.Direction.CW
            )
        }.asComposePath())
    }

    private fun longestDistanceToACorner(size: Size, offset: Offset?): Float {
        if (offset == null) {
            return hypot(size.width / 2f, size.height / 2f)
        }

        val topLeft = hypot(offset.x, offset.y)
        val topRight = hypot(size.width - offset.x, offset.y)
        val bottomLeft = hypot(offset.x, size.height - offset.y)
        val bottomRight = hypot(size.width - offset.x, size.height - offset.y)

        return maxOf(topLeft, topRight, bottomLeft, bottomRight)
    }
}




/*
@Composable
private fun CircularRevealTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColors()
    } else {
        lightColors()
    }
    MaterialTheme(
        c = colors,
        content = content
    )
}*/
