package sp.ax.jc.animations.style

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf

sealed interface SlideStyle {
    @Immutable
    data class Horizontal(
        val initialOffsetX: (fullWidth: Int) -> Int,
        val targetOffsetX: (fullWidth: Int) -> Int,
    ) : SlideStyle {
        companion object {
            /**
             * Show: [---] -> [--+] -> [-++] -> [+++]
             * Hide: [+++] -> [-++] -> [--+] -> [---]
             */
            val ToLeftToRight = Horizontal(
                initialOffsetX = { it },
                targetOffsetX = { it },
            )

            /**
             * Show: [---] -> [--+] -> [-++] -> [+++]
             * Hide: [+++] -> [++-] -> [+--] -> [---]
             */
            val ToLeftToLeft = Horizontal(
                initialOffsetX = { it },
                targetOffsetX = { -it },
            )
        }
    }
}

val LocalSlideHStyle = staticCompositionLocalOf {
    SlideStyle.Horizontal.ToLeftToRight
}
