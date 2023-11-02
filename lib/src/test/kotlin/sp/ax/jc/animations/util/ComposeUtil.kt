package sp.ax.jc.animations.util

import androidx.compose.ui.semantics.SemanticsNode
import androidx.compose.ui.test.MainTestClock
import kotlin.time.Duration

internal fun MainTestClock.advanceTimeBy(duration: Duration, ignoreFrameDuration: Boolean = false) {
    advanceTimeBy(milliseconds = duration.inWholeMilliseconds, ignoreFrameDuration = ignoreFrameDuration)
}

internal fun SemanticsNode.requireParent(): SemanticsNode {
    return parent ?: error("Parent does not exist!")
}
