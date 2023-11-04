package sp.ax.jc.animations.tween.fade

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.Easing
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import sp.ax.jc.animations.style.LocalFadeStyle
import sp.ax.jc.animations.style.LocalTweenStyle
import kotlin.time.Duration

/**
 * The wrapper for the [AnimatedVisibility][androidx.compose.animation.AnimatedVisibility] with an `enter` as [fadeIn] and `exit` as [fadeOut].
 *
 * Usage:
 * ```
 * val visibleState = remember { mutableStateOf(false) }
 * ...
 * FadeVisibility(
 *     visible = visibleState.value,
 *     inDuration = 3.seconds,
 *     inDelay = Duration.ZERO,
 *     inEasing = LinearEasing,
 *     initialOffset = 0f,
 *     outDuration = 3.seconds,
 *     outDelay = Duration.ZERO,
 *     outEasing = LinearEasing,
 *     targetOffset = 0f,
 * ) {
 *     BasicText(text = "foo")
 * }
 * ```
 *
 * Presentation:
 * ```
 * visibleState.value = true
 * alpha: [0.00] -> [0.25] -> [0.50] -> [0.75] -> [1.00]
 * visibleState.value = false
 * alpha: [1.00] -> [0.75] -> [0.50] -> [0.25] -> [0.00]
 * ```
 *
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.0.2
 */
@Suppress("LongParameterList", "FunctionNaming")
@Composable
fun FadeVisibility(
    visible: Boolean,
    modifier: Modifier = Modifier,
    label: String = "FadeVisibility",
    inDuration: Duration,
    inDelay: Duration,
    inEasing: Easing,
    initialAlpha: Float,
    outDuration: Duration,
    outDelay: Duration,
    outEasing: Easing,
    targetAlpha: Float,
    content: @Composable AnimatedVisibilityScope.() -> Unit,
) {
    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        label = label,
        enter = fadeIn(
            duration = inDuration,
            delay = inDelay,
            easing = inEasing,
            initialAlpha = initialAlpha,
        ),
        exit = fadeOut(
            duration = outDuration,
            delay = outDelay,
            easing = outEasing,
            targetAlpha = targetAlpha,
        ),
        content = content,
    )
}

/**
 * The wrapper for the [FadeVisibility] with one set of parameters for both in and out.
 *
 * Usage:
 * ```
 * val visibleState = remember { mutableStateOf(false) }
 * ...
 * FadeVisibility(
 *     visible = visibleState.value,
 *     duration = 3.seconds,
 *     delay = Duration.ZERO,
 *     easing = LinearEasing,
 *     initialOffset = 0f,
 *     targetOffset = 0f,
 * ) {
 *     BasicText(text = "foo")
 * }
 * ```
 * or
 * ```
 * val visibleState = remember { mutableStateOf(false) }
 * ...
 * FadeVisibility(visible = visibleState.value) {
 *     BasicText(text = "foo")
 * }
 * ```
 *
 * Presentation:
 * ```
 * visibleState.value = true
 * alpha: [0.00] -> [0.25] -> [0.50] -> [0.75] -> [1.00]
 * visibleState.value = false
 * alpha: [1.00] -> [0.75] -> [0.50] -> [0.25] -> [0.00]
 * ```
 *
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.0.2
 */
@Suppress("LongParameterList", "FunctionNaming")
@Composable
fun FadeVisibility(
    visible: Boolean,
    modifier: Modifier = Modifier,
    label: String = "FadeVisibility",
    duration: Duration = LocalTweenStyle.current.duration,
    delay: Duration = LocalTweenStyle.current.delay,
    easing: Easing = LocalTweenStyle.current.easing,
    initialAlpha: Float = LocalFadeStyle.current.initialAlpha,
    targetAlpha: Float = LocalFadeStyle.current.targetAlpha,
    content: @Composable AnimatedVisibilityScope.() -> Unit,
) {
    FadeVisibility(
        visible = visible,
        modifier = modifier,
        label = label,
        inDuration = duration,
        inDelay = delay,
        inEasing = easing,
        initialAlpha = initialAlpha,
        outDuration = duration,
        outDelay = delay,
        outEasing = easing,
        targetAlpha = targetAlpha,
        content = content,
    )
}
