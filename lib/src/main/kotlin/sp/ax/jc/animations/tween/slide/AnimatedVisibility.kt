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

/**
 * The wrapper for the [AnimatedVisibility][androidx.compose.animation.AnimatedVisibility] with an `enter` as [slideIn] and `exit` as [slideOut].
 *
 * Usage:
 * ```
 * val visibleState = remember { mutableStateOf(false) }
 * ...
 * SlideVisibility(
 *     visible = visibleState.value,
 *     inDuration = 3.seconds,
 *     inDelay = Duration.ZERO,
 *     inEasing = LinearEasing,
 *     initialOffset = { IntOffset(x = it.width, y = 0) },
 *     outDuration = 3.seconds,
 *     outDelay = Duration.ZERO,
 *     outEasing = LinearEasing,
 *     targetOffset = { IntOffset(x = it.width, y = 0) },
 * ) {
 *     BasicText(text = "foo")
 * }
 * ```
 *
 * Presentation:
 * ```
 * visibleState.value = true
 * [---] -> [--+] -> [-++] -> [+++]
 * visibleState.value = false
 * [+++] -> [-++] -> [--+] -> [---]
 * ```
 *
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.0.1
 */
@Suppress("LongParameterList", "FunctionNaming")
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

/**
 * The wrapper for the [SlideVisibility] with one set of parameters for both in and out.
 *
 * Usage:
 * ```
 * val visibleState = remember { mutableStateOf(false) }
 * ...
 * SlideVisibility(
 *     visible = visibleState.value,
 *     duration = 3.seconds,
 *     delay = Duration.ZERO,
 *     easing = LinearEasing,
 *     initialOffset = { IntOffset(x = it.width, y = 0) },
 *     targetOffset = { IntOffset(x = it.width, y = 0) },
 * ) {
 *     BasicText(text = "foo")
 * }
 * ```
 *
 * Presentation:
 * ```
 * visibleState.value = true
 * [---] -> [--+] -> [-++] -> [+++]
 * visibleState.value = false
 * [+++] -> [-++] -> [--+] -> [---]
 * ```
 *
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.0.1
 */
@Suppress("LongParameterList", "FunctionNaming")
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

/**
 * The wrapper for the [SlideVisibility] for horizontal transition only.
 *
 * Usage:
 * ```
 * val visibleState = remember { mutableStateOf(false) }
 * ...
 * SlideHVisibility(visible = visibleState.value) {
 *     BasicText(text = "foo")
 * }
 * ```
 *
 * @param duration duration of the animation spec. The default value is taken from [LocalTweenStyle].
 * @param delay the amount of time that animation waits before starting. The default value is taken from [LocalTweenStyle].
 * @param easing the easing curve that will be used to interpolate between start and end. The default value is taken from [LocalTweenStyle].
 * @param initialOffsetX a lambda that takes the full width of the content in pixels and returns the initial x-coordinate. The default value is taken from [LocalSlideHStyle].
 * @param targetOffsetX a lambda that takes the full width of the content and returns the target x-coordinate. The default value is taken from [LocalSlideHStyle].
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.0.1
 * @see LocalTweenStyle
 * @see LocalSlideHStyle
 */
@Suppress("LongParameterList", "FunctionNaming")
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
