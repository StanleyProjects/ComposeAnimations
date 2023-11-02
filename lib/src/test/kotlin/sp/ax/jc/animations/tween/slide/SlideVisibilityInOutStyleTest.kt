package sp.ax.jc.animations.tween.slide

import androidx.compose.animation.core.Ease
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.time.Duration.Companion.seconds

@Suppress("StringLiteralDuplication", "MagicNumber")
@RunWith(RobolectricTestRunner::class)
internal class SlideVisibilityInOutStyleTest : AnimatedTest() {
    @Test
    fun defaultAutoAdvanceTest() {
        val animatedContent = "animatedContent"
        val switcher = "switcher"
        val duration = .6.seconds
        val delay = .3.seconds
        val easing = Ease
        val getOffset: (fullSize: IntSize) -> IntOffset = { IntOffset(x = it.width, y = 0) }
        rule.setContent {
            Content(switcherTag = switcher) { visible: Boolean ->
                SlideVisibility(
                    visible = visible,
                    inDuration = duration,
                    inDelay = delay,
                    inEasing = easing,
                    initialOffset = getOffset,
                    outDuration = duration,
                    outDelay = delay,
                    outEasing = easing,
                    targetOffset = getOffset,
                ) {
                    AnimatedContent(testTag = animatedContent)
                }
            }
        }
        rule.onNodeWithTag(switcher).performClick()
        rule.onNodeWithTag(animatedContent).assertIsDisplayed()
        rule.onNodeWithTag(animatedContent).assertTextEquals(animatedContent)
        rule.onNodeWithTag(switcher).performClick()
    }

    @Test
    fun defaultTest() {
        val animatedContent = "animatedContent"
        val switcher = "switcher"
        val inDuration = .6.seconds
        val inDelay = .3.seconds
        val outDuration = .8.seconds
        val outDelay = .4.seconds
        val easing = Ease
        val getOffset: (fullSize: IntSize) -> IntOffset = { IntOffset(x = it.width, y = 0) }
        rule.setContent {
            Content(switcherTag = switcher) { visible: Boolean ->
                SlideVisibility(
                    visible = visible,
                    inDuration = inDuration,
                    inDelay = inDelay,
                    inEasing = easing,
                    initialOffset = getOffset,
                    outDuration = outDuration,
                    outDelay = outDelay,
                    outEasing = easing,
                    targetOffset = getOffset,
                ) {
                    AnimatedContent(testTag = animatedContent)
                }
            }
        }
        rule.assertAnimation(
            contentTag = animatedContent,
            performStartToFinish = {
                rule.onNodeWithTag(switcher).performClick()
            },
            performFinishToStart = {
                rule.onNodeWithTag(switcher).performClick()
            },
            inDuration = inDuration,
            inDelay = inDelay,
            outDuration = outDuration,
            outDelay = outDelay,
            onContentReady = {
                rule.onNodeWithTag(animatedContent).assertTextEquals(animatedContent)
            },
        )
    }

    @Test
    fun labelTest() {
        val animatedContent = "animatedContent"
        val switcher = "switcher"
        val label = "label"
        val inDuration = .6.seconds
        val inDelay = .3.seconds
        val outDuration = .8.seconds
        val outDelay = .4.seconds
        val easing = Ease
        val getOffset: (fullSize: IntSize) -> IntOffset = { IntOffset(x = it.width, y = 0) }
        rule.setContent {
            Content(switcherTag = switcher) { visible: Boolean ->
                SlideVisibility(
                    visible = visible,
                    label = label,
                    inDuration = inDuration,
                    inDelay = inDelay,
                    inEasing = easing,
                    initialOffset = getOffset,
                    outDuration = outDuration,
                    outDelay = outDelay,
                    outEasing = easing,
                    targetOffset = getOffset,
                ) {
                    AnimatedContent(testTag = animatedContent)
                }
            }
        }
        rule.assertAnimation(
            contentTag = animatedContent,
            performStartToFinish = {
                rule.onNodeWithTag(switcher).performClick()
            },
            performFinishToStart = {
                rule.onNodeWithTag(switcher).performClick()
            },
            inDuration = inDuration,
            inDelay = inDelay,
            outDuration = outDuration,
            outDelay = outDelay,
            onContentReady = {
                rule.onNodeWithTag(animatedContent).assertTextEquals(animatedContent)
            },
        )
    }

    @Test
    fun modifierTest() {
        val animatedContainer = "animatedContainer"
        val animatedContent = "animatedContent"
        val switcher = "switcher"
        val inDuration = .6.seconds
        val inDelay = .3.seconds
        val outDuration = .8.seconds
        val outDelay = .4.seconds
        val easing = Ease
        val getOffset: (fullSize: IntSize) -> IntOffset = { IntOffset(x = it.width, y = 0) }
        rule.setContent {
            Content(switcherTag = switcher) { visible: Boolean ->
                SlideVisibility(
                    visible = visible,
                    modifier = Modifier.testTag(animatedContainer),
                    inDuration = inDuration,
                    inDelay = inDelay,
                    inEasing = easing,
                    initialOffset = getOffset,
                    outDuration = outDuration,
                    outDelay = outDelay,
                    outEasing = easing,
                    targetOffset = getOffset,
                ) {
                    AnimatedContent(testTag = animatedContent)
                }
            }
        }
        rule.assertAnimation(
            containerTag = animatedContainer,
            contentTag = animatedContent,
            performStartToFinish = {
                rule.onNodeWithTag(switcher).performClick()
            },
            performFinishToStart = {
                rule.onNodeWithTag(switcher).performClick()
            },
            inDuration = inDuration,
            inDelay = inDelay,
            outDuration = outDuration,
            outDelay = outDelay,
            onContentReady = {
                rule.onNodeWithTag(animatedContent).assertTextEquals(animatedContent)
            },
        )
    }
}
