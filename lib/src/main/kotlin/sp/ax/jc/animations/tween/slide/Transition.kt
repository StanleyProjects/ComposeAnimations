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
