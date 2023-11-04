package sp.ax.jc.animations.style

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf

/**
 * Stores values to describe how to fade.
 * @property initialAlpha the starting alpha of the enter transition
 * @property targetAlpha the target alpha of the exit transition
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.0.2
 * @see androidx.compose.animation.fadeIn
 * @see androidx.compose.animation.fadeOut
 */
@Immutable
data class FadeStyle(
    val initialAlpha: Float,
    val targetAlpha: Float,
) {
    companion object {
        @Suppress("UndocumentedPublicProperty")
        val Default = FadeStyle(
            initialAlpha = 0f,
            targetAlpha = 0f,
        )
    }
}

/**
 * Provides [FadeStyle] to describe how to fade. Default is [FadeStyle.Default].
 *
 * Usage:
 * ```
 * val visibleState = remember { mutableStateOf(false) }
 * ...
 * CompositionLocalProvider(
 *     LocalFadeStyle provides FadeStyle(
 *         initialAlpha = 0.25f,
 *         targetAlpha = 0.75f,
 *     ),
 * ) {
 *     FadeVisibility(visible = visibleState.value) {
 *         BasicText(text = "foo")
 *     }
 * }
 * ```
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.0.2
 */
val LocalFadeStyle = staticCompositionLocalOf {
    FadeStyle.Default
}
