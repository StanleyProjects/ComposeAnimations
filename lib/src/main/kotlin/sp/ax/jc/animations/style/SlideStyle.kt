package sp.ax.jc.animations.style

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf

sealed interface SlideStyle {
    /**
     * Stores values to describe how to do enter and exit transitions horizontally.
     * @param initialOffsetX a lambda that takes the full width of the content in pixels and returns the initial x-coordinate
     * @param targetOffsetX a lambda that takes the full width of the content and returns the target x-coordinate
     * @author [Stanley Wintergreen](https://github.com/kepocnhh)
     * @since 0.0.1
     */
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

/**
 * Provides [SlideStyle.Horizontal] to describe how to do enter and exit transitions horizontally.
 *
 * Usage:
 * ```
 * val visibleState = remember { mutableStateOf(false) }
 * ...
 * CompositionLocalProvider(
 *     LocalSlideHStyle provides SlideStyle.Horizontal(
 *         initialOffset = { IntOffset(x = it.width, y = 0) },
 *         targetOffsetX = { IntOffset(x = it.width, y = 0) },
 *     ),
 * ) {
 *     SlideHVisibility(visible = visibleState.value) {
 *         BasicText(text = "foo")
 *     }
 * }
 * ```
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.0.1
 */
val LocalSlideHStyle = staticCompositionLocalOf {
    SlideStyle.Horizontal.ToLeftToRight
}
