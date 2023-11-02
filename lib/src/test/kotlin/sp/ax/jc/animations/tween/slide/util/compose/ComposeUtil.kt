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
import sp.ax.jc.animations.tween.slide.assertAnimationBegin
import sp.ax.jc.animations.tween.slide.assertAnimationFinish
import sp.ax.jc.animations.tween.slide.assertAnimationInTheMiddle
import sp.ax.jc.animations.tween.slide.assertAnimationPreStart
import sp.ax.jc.animations.util.advanceTimeBy
import sp.ax.jc.animations.util.requireParent
import kotlin.time.Duration

@Deprecated(message = "todo")
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
    offsetXRangeInTheMiddleInitial: (parentSize: IntSize) -> Pair<Float, Float> = { 0f to it.width.toFloat() },
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
    provider.assertAnimationBegin(
        containerTag = containerTag,
        contentTag = contentTag,
        expectedOffset = expectedOffsetOnInitial,
    )
    mainClock.advanceTimeBy(delay)
    mainClock.advanceTimeBy(duration.div(2))
    provider.assertAnimationInTheMiddle(
        containerTag = containerTag,
        contentTag = contentTag,
        offsetXRange = offsetXRangeInTheMiddleInitial,
    )
    mainClock.advanceTimeBy(duration.div(2))
    provider.assertAnimationFinish(
        containerTag = containerTag,
        contentTag = contentTag,
    )
    onContentReady()
    performFinishToStart()
    mainClock.advanceTimeBy(delay)
    mainClock.advanceTimeBy(duration.div(2))
    provider.assertAnimationInTheMiddle(
        containerTag = containerTag,
        contentTag = contentTag,
        offsetXRange = offsetXRangeInTheMiddleTarget,
    )
    mainClock.advanceTimeBy(duration.div(2))
    provider.assertAnimationBegin(
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
