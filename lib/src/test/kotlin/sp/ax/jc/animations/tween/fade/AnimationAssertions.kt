package sp.ax.jc.animations.tween.fade

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onNodeWithTag
import sp.ax.jc.animations.util.advanceTimeBy
import kotlin.time.Duration

internal fun ComposeTestRule.assertAnimation(
    contentTag: String,
    performStartToFinish: () -> Unit,
    inDelay: Duration,
    inDuration: Duration,
    onContentReady: () -> Unit,
    performFinishToStart: () -> Unit,
    outDelay: Duration,
    outDuration: Duration,
) {
    mainClock.autoAdvance = false
    onNodeWithTag(contentTag).assertDoesNotExist()
    performStartToFinish()
    mainClock.advanceTimeByFrame()
    onNodeWithTag(contentTag).assertIsDisplayed()
    // todo test initial alpha
    mainClock.advanceTimeBy(inDelay)
    mainClock.advanceTimeBy(inDuration / 2)
    onNodeWithTag(contentTag).assertIsDisplayed()
    // todo test alpha in the middle
    mainClock.advanceTimeBy(inDuration / 2)
    onNodeWithTag(contentTag).assertIsDisplayed()
    // todo test finish alpha
    onContentReady()
    performFinishToStart()
    mainClock.advanceTimeBy(outDelay)
    mainClock.advanceTimeBy(outDuration / 2)
    onNodeWithTag(contentTag).assertIsDisplayed()
    // todo test alpha in the middle
    mainClock.advanceTimeBy(outDuration / 2)
    onNodeWithTag(contentTag).assertIsDisplayed()
    // todo test target alpha
    mainClock.autoAdvance = true
    onNodeWithTag(contentTag).assertDoesNotExist()
}

internal fun ComposeTestRule.assertAnimation(
    containerTag: String,
    contentTag: String,
    performStartToFinish: () -> Unit,
    inDelay: Duration,
    inDuration: Duration,
    onContentReady: () -> Unit,
    performFinishToStart: () -> Unit,
    outDelay: Duration,
    outDuration: Duration,
) {
    mainClock.autoAdvance = false
    onNodeWithTag(containerTag).assertDoesNotExist()
    performStartToFinish()
    mainClock.advanceTimeByFrame()
    onNodeWithTag(containerTag)
        .assertIsDisplayed()
        .onChild()
        .assert(hasTestTag(contentTag))
        .assertIsDisplayed()
    // todo test initial alpha
    mainClock.advanceTimeBy(inDelay)
    mainClock.advanceTimeBy(inDuration / 2)
    onNodeWithTag(containerTag)
        .assertIsDisplayed()
        .onChild()
        .assert(hasTestTag(contentTag))
        .assertIsDisplayed()
    // todo test alpha in the middle
    mainClock.advanceTimeBy(inDuration / 2)
    onNodeWithTag(containerTag)
        .assertIsDisplayed()
        .onChild()
        .assert(hasTestTag(contentTag))
        .assertIsDisplayed()
    // todo test finish alpha
    onContentReady()
    performFinishToStart()
    mainClock.advanceTimeBy(outDelay)
    mainClock.advanceTimeBy(outDuration / 2)
    onNodeWithTag(containerTag)
        .assertIsDisplayed()
        .onChild()
        .assert(hasTestTag(contentTag))
        .assertIsDisplayed()
    // todo test alpha in the middle
    mainClock.advanceTimeBy(outDuration / 2)
    onNodeWithTag(containerTag)
        .assertIsDisplayed()
        .onChild()
        .assert(hasTestTag(contentTag))
        .assertIsDisplayed()
    // todo test target alpha
    mainClock.autoAdvance = true
    onNodeWithTag(containerTag).assertDoesNotExist()
}
