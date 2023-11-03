package sp.ax.jc.animations.tween

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import kotlin.time.Duration

/**
 * Creates a [TweenSpec] configured with the given duration, delay and easing curve.
 *
 * @param duration duration of the animation spec
 * @param delay the amount of time that animation waits before starting
 * @param easing the easing curve that will be used to interpolate between start and end
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.0.1
 */
fun <T> tween(
    duration: Duration,
    delay: Duration,
    easing: Easing,
): TweenSpec<T> {
    return tween(
        durationMillis = duration.inWholeMilliseconds.toInt(),
        delayMillis = delay.inWholeMilliseconds.toInt(),
        easing = easing,
    )
}
