package sp.ax.jc.animations.tween

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import kotlin.time.Duration

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
