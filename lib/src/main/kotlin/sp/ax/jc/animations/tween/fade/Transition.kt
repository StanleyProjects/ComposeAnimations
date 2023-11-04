package sp.ax.jc.animations.tween.fade

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Easing
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import sp.ax.jc.animations.tween.tween
import kotlin.time.Duration

/**
 * The wrapper for the [fadeIn][androidx.compose.animation.fadeIn] with an `animationSpec` as [TweenSpec][androidx.compose.animation.core.TweenSpec].
 *
 * Usage:
 * ```
 * fadeIn(
 *     duration = 3.seconds,
 *     delay = 1.seconds,
 *     easing = LinearEasing,
 *     initialAlpha = .4f,
 * )
 * ```
 *
 * Presentation:
 * ```
 *     ^ alpha
 *     |
 * 1.0 +                   *
 * 0.8 +               *
 * 0.6 +           *
 * 0.4 +       *
 * 0.2 +
 * 0.0 +   *
 *     |
 *     + - + - + - + - + - + - > seconds
 *         0   1   2   3   4
 * ```
 *
 * @param duration duration of the animation spec
 * @param delay the amount of time that animation waits before starting
 * @param easing the easing curve that will be used to interpolate between start and end
 * @param initialAlpha the starting alpha of the enter transition
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.0.2
 */
fun fadeIn(
    duration: Duration,
    delay: Duration,
    easing: Easing,
    initialAlpha: Float,
): EnterTransition {
    return fadeIn(
        animationSpec = tween(
            duration = duration,
            delay = delay,
            easing = easing,
        ),
        initialAlpha = initialAlpha,
    )
}

/**
 * The wrapper for the [fadeOut][androidx.compose.animation.fadeOut] with an `animationSpec` as [TweenSpec][androidx.compose.animation.core.TweenSpec].
 *
 * Usage:
 * ```
 * fadeOut(
 *     duration = 3.seconds,
 *     delay = 1.seconds,
 *     easing = LinearEasing,
 *     targetAlpha = .4f,
 * )
 * ```
 *
 * Presentation:
 * ```
 *     ^ alpha
 *     |
 * 1.0 +   *   *
 * 0.8 +           *
 * 0.6 +               *
 * 0.4 +                   *
 * 0.2 +
 * 0.0 +
 *     |
 *     + - + - + - + - + - + - > seconds
 *         0   1   2   3   4
 * ```
 *
 * @param duration duration of the animation spec
 * @param delay the amount of time that animation waits before starting
 * @param easing the easing curve that will be used to interpolate between start and end
 * @param targetAlpha the target alpha of the exit transition
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.0.2
 */
fun fadeOut(
    duration: Duration,
    delay: Duration,
    easing: Easing,
    targetAlpha: Float,
): ExitTransition {
    return fadeOut(
        animationSpec = tween(
            duration = duration,
            delay = delay,
            easing = easing,
        ),
        targetAlpha = targetAlpha,
    )
}
