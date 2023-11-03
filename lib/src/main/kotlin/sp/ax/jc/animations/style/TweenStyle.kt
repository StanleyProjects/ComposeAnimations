package sp.ax.jc.animations.style

import androidx.compose.animation.core.AnimationConstants
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

/**
 * Stores values to describe how to animate with [TweenSpec][androidx.compose.animation.core.TweenSpec].
 * @param duration duration of the animation spec
 * @param delay the amount of time that animation waits before starting
 * @param easing the easing curve that will be used to interpolate between start and end
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.0.1
 */
@Immutable
data class TweenStyle(
    val duration: Duration,
    val delay: Duration,
    val easing: Easing,
)

/**
 * Provides [TweenStyle] to describe how to animate with [TweenSpec][androidx.compose.animation.core.TweenSpec].
 *
 * Usage:
 * ```
 * val visibleState = remember { mutableStateOf(false) }
 * ...
 * CompositionLocalProvider(
 *     LocalTweenStyle provides TweenStyle(
 *         duration = 1.seconds,
 *         delay = Duration.ZERO,
 *         easing = LinearEasing,
 *     ),
 * ) {
 *     SlideHVisibility(visible = visibleState.value) {
 *         BasicText(text = "foo")
 *     }
 * }
 * ```
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.0.1
 */
val LocalTweenStyle = staticCompositionLocalOf {
    TweenStyle(
        duration = AnimationConstants.DefaultDurationMillis.milliseconds,
        delay = Duration.ZERO,
        easing = FastOutSlowInEasing,
    )
}
