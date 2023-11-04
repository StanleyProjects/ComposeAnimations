package sp.ax.jc.animations.style

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf

@Immutable
data class FadeStyle(
    val initialAlpha: Float,
    val targetAlpha: Float,
) {
    companion object {
        val Default = FadeStyle(
            initialAlpha = 0f,
            targetAlpha = 0f,
        )
    }
}

val LocalFadeStyle = staticCompositionLocalOf {
    FadeStyle.Default
}
