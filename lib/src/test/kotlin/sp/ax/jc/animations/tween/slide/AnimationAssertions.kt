package sp.ax.jc.animations.tween.slide

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.onNodeWithTag

internal fun SemanticsNodeInteractionsProvider.assertAnimationPreStart(
    contentTag: String,
) {
    onNodeWithTag(contentTag).assertDoesNotExist()
}

internal fun SemanticsNodeInteractionsProvider.assertAnimationPreStart(
    containerTag: String,
    contentTag: String,
) {
    onNodeWithTag(containerTag).assertDoesNotExist()
    onNodeWithTag(contentTag).assertDoesNotExist()
}