package sp.ax.jc.animations.tween.fade

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Easing
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import sp.ax.jc.animations.tween.tween
import kotlin.time.Duration

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
