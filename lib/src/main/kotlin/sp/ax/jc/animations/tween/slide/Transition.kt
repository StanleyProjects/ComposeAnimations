package sp.ax.jc.animations.tween.slide

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Easing
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import sp.ax.jc.animations.tween.tween
import kotlin.time.Duration

/**
 * The wrapper for the [slideIn][androidx.compose.animation.slideIn] with an `animationSpec` as [TweenSpec][androidx.compose.animation.core.TweenSpec].
 *
 * Usage:
 * ```
 * slideIn(
 *     duration = 3.seconds,
 *     delay = 1.seconds,
 *     easing = LinearEasing,
 *     initialOffset = { IntOffset(x = it.width, y = 0) }
 * )
 * ```
 *
 * ```
 *   ^ x-coordinate
 *   |
 * w +   *   *
 *   |
 *   +           *
 *   |
 *   +               *
 *   |
 * 0 +                   *
 *   |
 *   + - + - + - + - + - + - > seconds
 *       0   1   2   3   4
 * ```
 *
 * @param duration duration of the animation spec
 * @param delay the amount of time that animation waits before starting
 * @param easing the easing curve that will be used to interpolate between start and end
 * @param initialOffset a lambda that takes the full size of the content and returns the initial offset for the slide-in
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.0.1
 */
fun slideIn(
    duration: Duration,
    delay: Duration,
    easing: Easing,
    initialOffset: (fullSize: IntSize) -> IntOffset,
): EnterTransition {
    return slideIn(
        animationSpec = tween(
            duration = duration,
            delay = delay,
            easing = easing,
        ),
        initialOffset = initialOffset,
    )
}

/**
 * The wrapper for the [slideOut][androidx.compose.animation.slideOut] with an `animationSpec` as [TweenSpec][androidx.compose.animation.core.TweenSpec].
 *
 * Usage:
 * ```
 * slideOut(
 *     duration = 3.seconds,
 *     delay = 1.seconds,
 *     easing = LinearEasing,
 *     targetOffset = { IntOffset(x = it.width, y = 0) }
 * )
 * ```
 *
 * ```
 *   ^ x-coordinate
 *   |
 * w +                   *
 *   |
 *   +               *
 *   |
 *   +           *
 *   |
 * 0 +   *   *
 *   |
 *   + - + - + - + - + - + - > seconds
 *       0   1   2   3   4
 * ```
 *
 * @param duration duration of the animation spec
 * @param delay the amount of time that animation waits before starting
 * @param easing the easing curve that will be used to interpolate between start and end
 * @param targetOffset a lambda that takes the full size of the content and returns the target offset for the slide-out
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.0.1
 */
fun slideOut(
    duration: Duration,
    delay: Duration,
    easing: Easing,
    targetOffset: (fullSize: IntSize) -> IntOffset,
): ExitTransition {
    return slideOut(
        animationSpec = tween(
            duration = duration,
            delay = delay,
            easing = easing,
        ),
        targetOffset = targetOffset,
    )
}
