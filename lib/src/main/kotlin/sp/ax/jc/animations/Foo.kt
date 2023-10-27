package sp.ax.jc.animations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlin.time.Duration

@Composable
fun AnimatedFadeVisibility(
    visible: Boolean,
    modifier: Modifier = Modifier,
    label: String = "AnimatedVisibility:fade",
    animationSpec: FiniteAnimationSpec<Float>,
    outAnimationSpec: FiniteAnimationSpec<Float> = animationSpec,
    content: @Composable AnimatedVisibilityScope.() -> Unit,
) {
    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        label = label,
        enter = fadeIn(animationSpec),
        exit = fadeOut(outAnimationSpec),
        content = content,
    )
}

@Composable
fun AnimatedFadeVisibility(
    visible: Boolean,
    modifier: Modifier = Modifier,
    label: String = "AnimatedVisibility:fade:tween",
    duration: Duration,
    outDuration: Duration = duration,
    content: @Composable AnimatedVisibilityScope.() -> Unit,
) {
    AnimatedFadeVisibility(
        visible = visible,
        modifier = modifier,
        label = label,
        animationSpec = tween(duration.inWholeMilliseconds.toInt()),
        outAnimationSpec = tween(outDuration.inWholeMilliseconds.toInt()),
        content = content,
    )
}
