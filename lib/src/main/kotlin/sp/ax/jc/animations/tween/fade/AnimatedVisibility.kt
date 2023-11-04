package sp.ax.jc.animations.tween.fade

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.Easing
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import sp.ax.jc.animations.style.LocalFadeStyle
import sp.ax.jc.animations.style.LocalTweenStyle
import kotlin.time.Duration

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
