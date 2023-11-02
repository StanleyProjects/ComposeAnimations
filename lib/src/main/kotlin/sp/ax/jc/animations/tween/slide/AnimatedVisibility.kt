package sp.ax.jc.animations.tween.slide

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.Easing
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import sp.ax.jc.animations.style.LocalSlideHStyle
import sp.ax.jc.animations.style.LocalTweenStyle
import kotlin.time.Duration

@Composable
fun SlideVisibility(
    visible: Boolean,
    modifier: Modifier = Modifier,
    label: String = "SlideVisibility",
    inDuration: Duration,
    inDelay: Duration,
    inEasing: Easing,
    initialOffset: (fullSize: IntSize) -> IntOffset,
    outDuration: Duration,
    outDelay: Duration,
    outEasing: Easing,
    targetOffset: (fullSize: IntSize) -> IntOffset,
    content: @Composable AnimatedVisibilityScope.() -> Unit,
) {
    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        label = label,
        enter = slideIn(
            duration = inDuration,
            delay = inDelay,
            easing = inEasing,
            initialOffset = initialOffset,
        ),
        exit = slideOut(
            duration = outDuration,
            delay = outDelay,
            easing = outEasing,
            targetOffset = targetOffset,
        ),
        content = content,
    )
}

@Composable
fun SlideVisibility(
    visible: Boolean,
    modifier: Modifier = Modifier,
    label: String = "SlideVisibility",
    duration: Duration,
    delay: Duration,
    easing: Easing,
    initialOffset: (fullSize: IntSize) -> IntOffset,
    targetOffset: (fullSize: IntSize) -> IntOffset,
    content: @Composable AnimatedVisibilityScope.() -> Unit,
) {
    SlideVisibility(
        visible = visible,
        modifier = modifier,
        label = label,
        inDuration = duration,
        inDelay = delay,
        inEasing = easing,
        initialOffset = initialOffset,
        outDuration = duration,
        outDelay = delay,
        outEasing = easing,
        targetOffset = targetOffset,
        content = content,
    )
}

@Composable
fun SlideHVisibility(
    visible: Boolean,
    modifier: Modifier = Modifier,
    label: String = "SlideHVisibility",
    duration: Duration = LocalTweenStyle.current.duration,
    delay: Duration = LocalTweenStyle.current.delay,
    easing: Easing = LocalTweenStyle.current.easing,
    initialOffsetX: (fullWidth: Int) -> Int = LocalSlideHStyle.current.initialOffsetX,
    targetOffsetX: (fullWidth: Int) -> Int = LocalSlideHStyle.current.targetOffsetX,
    content: @Composable AnimatedVisibilityScope.() -> Unit,
) {
    SlideVisibility(
        visible = visible,
        modifier = modifier,
        label = label,
        duration = duration,
        delay = delay,
        easing = easing,
        initialOffset = { IntOffset(x = initialOffsetX(it.width), y = 0) },
        targetOffset = { IntOffset(x = targetOffsetX(it.width), y = 0) },
        content = content,
    )
}
