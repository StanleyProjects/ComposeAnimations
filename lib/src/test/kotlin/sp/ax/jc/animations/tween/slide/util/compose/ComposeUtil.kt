package sp.ax.jc.animations.tween.slide.util.compose

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.MainTestClock
import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.IntSize
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import sp.ax.jc.animations.tween.slide.assertAnimationPreStart
import sp.ax.jc.animations.util.advanceTimeBy
import sp.ax.jc.animations.util.requireParent
import kotlin.time.Duration

private fun assertAnimationBegin(
    provider: SemanticsNodeInteractionsProvider,
    containerTag: String,
    contentTag: String,
    expectedOffset: (parentSize: IntSize) -> Offset,
) {
    provider.onNodeWithTag(containerTag).assertIsDisplayed()
    provider.onNodeWithTag(contentTag).also { node ->
        node.assertExists()
        node.assertIsNotDisplayed()
        val sn = node.fetchSemanticsNode()
        assertEquals(expectedOffset(sn.requireParent().size), sn.positionInRoot)
    }
}

private fun assertAnimationInTheMiddle(
    provider: SemanticsNodeInteractionsProvider,
    containerTag: String,
    contentTag: String,
    offsetXRange: (parentSize: IntSize) -> Pair<Float, Float>,
) {
    provider.onNodeWithTag(containerTag).assertIsDisplayed()
    provider.onNodeWithTag(contentTag).also { node ->
        node.assertExists()
        node.assertIsNotDisplayed()
        val sn = node.fetchSemanticsNode()
        val (xMin, xMax) = offsetXRange(sn.requireParent().size)
        val message = "Node.x = ${sn.positionInRoot.x}, but range is $xMin..$xMax"
        assertTrue(message, sn.positionInRoot.x > xMin)
        assertTrue(message, sn.positionInRoot.x < xMax)
    }
}

private fun assertAnimationFinish(
    provider: SemanticsNodeInteractionsProvider,
    containerTag: String,
    contentTag: String,
    expectedOffset: (parentSize: IntSize) -> Offset = { Offset.Zero },
) {
    provider.onNodeWithTag(containerTag).assertIsDisplayed()
    provider.onNodeWithTag(contentTag).also { node ->
        node.assertExists()
        node.assertIsDisplayed()
        val sn = node.fetchSemanticsNode()
        assertEquals(expectedOffset(sn.requireParent().size), sn.positionInRoot)
    }
}

internal fun assertAnimation(
    provider: SemanticsNodeInteractionsProvider,
    containerTag: String,
    contentTag: String,
    mainClock: MainTestClock,
    performStartToFinish: () -> Unit,
    performFinishToStart: () -> Unit,
    delay: Duration,
    duration: Duration,
    expectedOffsetOnInitial: (parentSize: IntSize) -> Offset = { Offset(x = it.width.toFloat(), y = 0f) },
    offsetXRangeInTheMiddleInitial: (parentSize: IntSize) -> Pair<Float, Float> = {
        val min = 0f
        val max = it.width.toFloat()
        min to max
    },
    offsetXRangeInTheMiddleTarget: (parentSize: IntSize) -> Pair<Float, Float> = offsetXRangeInTheMiddleInitial,
    expectedOffsetOnTarget: (parentSize: IntSize) -> Offset = expectedOffsetOnInitial,
    onContentReady: () -> Unit,
) {
    check(!mainClock.autoAdvance)
    provider.assertAnimationPreStart(
        containerTag = containerTag,
        contentTag = contentTag,
    )
    performStartToFinish()
    mainClock.advanceTimeByFrame()
    assertAnimationBegin(
        provider = provider,
        containerTag = containerTag,
        contentTag = contentTag,
        expectedOffset = expectedOffsetOnInitial,
    )
    mainClock.advanceTimeBy(delay)
    mainClock.advanceTimeBy(duration.div(2))
    assertAnimationInTheMiddle(
        provider = provider,
        containerTag = containerTag,
        contentTag = contentTag,
        offsetXRange = offsetXRangeInTheMiddleInitial,
    )
    mainClock.advanceTimeBy(duration.div(2))
    assertAnimationFinish(
        provider = provider,
        containerTag = containerTag,
        contentTag = contentTag,
    )
    onContentReady()
    performFinishToStart()
    mainClock.advanceTimeBy(delay)
    mainClock.advanceTimeBy(duration.div(2))
    assertAnimationInTheMiddle(
        provider = provider,
        containerTag = containerTag,
        contentTag = contentTag,
        offsetXRange = offsetXRangeInTheMiddleTarget,
    )
    mainClock.advanceTimeBy(duration.div(2))
    assertAnimationBegin(
        provider = provider,
        containerTag = containerTag,
        contentTag = contentTag,
        expectedOffset = expectedOffsetOnTarget,
    )
    mainClock.advanceTimeByFrame()
    provider.assertAnimationPreStart(
        containerTag = containerTag,
        contentTag = contentTag,
    )
}
