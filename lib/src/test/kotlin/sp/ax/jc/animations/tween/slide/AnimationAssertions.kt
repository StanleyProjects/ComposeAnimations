package sp.ax.jc.animations.tween.slide

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.IntSize
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import sp.ax.jc.animations.util.advanceTimeBy
import sp.ax.jc.animations.util.requireParent
import kotlin.time.Duration

private fun SemanticsNodeInteraction.assertAnimationBegin(
    expectedOffset: (parentSize: IntSize) -> Offset,
) {
    val sn = assertExists()
        .assertIsNotDisplayed()
        .fetchSemanticsNode()
    assertEquals(expectedOffset(sn.requireParent().size), sn.positionInRoot)
}

private fun SemanticsNodeInteraction.assertAnimationInTheMiddle(
    offsetXRange: (parentSize: IntSize) -> Pair<Float, Float>,
) {
    val sn = assertExists()
        .assertIsNotDisplayed()
        .fetchSemanticsNode()
    val (xMin, xMax) = offsetXRange(sn.requireParent().size)
    val message = "Node.x = ${sn.positionInRoot.x}, but range is $xMin..$xMax"
    assertTrue(message, sn.positionInRoot.x > xMin)
    assertTrue(message, sn.positionInRoot.x < xMax)
}

private fun SemanticsNodeInteraction.assertAnimationFinish(
    expectedOffset: (parentSize: IntSize) -> Offset = { Offset.Zero },
) {
    val sn = assertExists()
        .assertIsDisplayed()
        .fetchSemanticsNode()
    assertEquals(expectedOffset(sn.requireParent().size), sn.positionInRoot)
}

private val DEFAULT_GET_EXPECTED_OFFSET: (parentSize: IntSize) -> Offset = { Offset(x = it.width.toFloat(), y = 0f) }
private val DEFAULT_GET_OFFSET_X_RANGE: (parentSize: IntSize) -> Pair<Float, Float> = { 0f to it.width.toFloat() }

@Suppress("LongParameterList")
internal fun ComposeTestRule.assertAnimation(
    containerTag: String,
    contentTag: String,
    performStartToFinish: () -> Unit,
    performFinishToStart: () -> Unit,
    inDelay: Duration,
    inDuration: Duration,
    outDelay: Duration,
    outDuration: Duration,
    expectedOffsetOnInitial: (parentSize: IntSize) -> Offset = DEFAULT_GET_EXPECTED_OFFSET,
    expectedOffsetOnTarget: (parentSize: IntSize) -> Offset = expectedOffsetOnInitial,
    offsetXRangeInTheMiddleInitial: (parentSize: IntSize) -> Pair<Float, Float> = DEFAULT_GET_OFFSET_X_RANGE,
    offsetXRangeInTheMiddleTarget: (parentSize: IntSize) -> Pair<Float, Float> = offsetXRangeInTheMiddleInitial,
    onContentReady: () -> Unit,
) {
    mainClock.autoAdvance = false
    onNodeWithTag(containerTag).assertDoesNotExist()
    onNodeWithTag(contentTag).assertDoesNotExist()
    performStartToFinish()
    mainClock.advanceTimeByFrame()
    onNodeWithTag(containerTag).assertIsDisplayed()
    onNodeWithTag(contentTag).assertAnimationBegin(expectedOffset = expectedOffsetOnInitial)
    mainClock.advanceTimeBy(inDelay)
    mainClock.advanceTimeBy(inDuration.div(2))
    onNodeWithTag(containerTag).assertIsDisplayed()
    onNodeWithTag(contentTag).assertAnimationInTheMiddle(offsetXRange = offsetXRangeInTheMiddleInitial)
    mainClock.advanceTimeBy(inDuration.div(2))
    onNodeWithTag(containerTag).assertIsDisplayed()
    onNodeWithTag(contentTag).assertAnimationFinish()
    onContentReady()
    performFinishToStart()
    mainClock.advanceTimeBy(outDelay)
    mainClock.advanceTimeBy(outDuration.div(2))
    onNodeWithTag(containerTag).assertIsDisplayed()
    onNodeWithTag(contentTag).assertAnimationInTheMiddle(offsetXRange = offsetXRangeInTheMiddleTarget)
    mainClock.advanceTimeBy(outDuration.div(2))
    onNodeWithTag(containerTag).assertIsDisplayed()
    onNodeWithTag(contentTag).assertAnimationBegin(expectedOffset = expectedOffsetOnTarget)
    mainClock.autoAdvance = true
    onNodeWithTag(containerTag).assertDoesNotExist()
}

@Suppress("LongParameterList")
internal fun ComposeTestRule.assertAnimation(
    contentTag: String,
    performStartToFinish: () -> Unit,
    performFinishToStart: () -> Unit,
    inDelay: Duration,
    inDuration: Duration,
    outDelay: Duration,
    outDuration: Duration,
    expectedOffsetOnInitial: (parentSize: IntSize) -> Offset = DEFAULT_GET_EXPECTED_OFFSET,
    expectedOffsetOnTarget: (parentSize: IntSize) -> Offset = expectedOffsetOnInitial,
    offsetXRangeInTheMiddleInitial: (parentSize: IntSize) -> Pair<Float, Float> = DEFAULT_GET_OFFSET_X_RANGE,
    offsetXRangeInTheMiddleTarget: (parentSize: IntSize) -> Pair<Float, Float> = offsetXRangeInTheMiddleInitial,
    onContentReady: () -> Unit,
) {
    mainClock.autoAdvance = false
    onNodeWithTag(contentTag).assertDoesNotExist()
    performStartToFinish()
    mainClock.advanceTimeByFrame()
    onNodeWithTag(contentTag).assertAnimationBegin(expectedOffset = expectedOffsetOnInitial)
    mainClock.advanceTimeBy(inDelay)
    mainClock.advanceTimeBy(inDuration.div(2))
    onNodeWithTag(contentTag).assertAnimationInTheMiddle(offsetXRange = offsetXRangeInTheMiddleInitial)
    mainClock.advanceTimeBy(inDuration.div(2))
    onNodeWithTag(contentTag).assertAnimationFinish()
    onContentReady()
    performFinishToStart()
    mainClock.advanceTimeBy(outDelay)
    mainClock.advanceTimeBy(outDuration.div(2))
    onNodeWithTag(contentTag).assertAnimationInTheMiddle(offsetXRange = offsetXRangeInTheMiddleTarget)
    mainClock.advanceTimeBy(outDuration.div(2))
    onNodeWithTag(contentTag).assertAnimationBegin(expectedOffset = expectedOffsetOnTarget)
    mainClock.autoAdvance = true
    onNodeWithTag(contentTag).assertDoesNotExist()
}
