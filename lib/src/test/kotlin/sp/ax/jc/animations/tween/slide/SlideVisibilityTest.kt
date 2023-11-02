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
internal class SlideVisibilityTest : AnimatedTest() {
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
                    duration = duration,
                    delay = delay,
                    easing = easing,
                    initialOffset = getOffset,
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
        val duration = .6.seconds
        val delay = .3.seconds
        val easing = Ease
        val getOffset: (fullSize: IntSize) -> IntOffset = { IntOffset(x = it.width, y = 0) }
        rule.setContent {
            Content(switcherTag = switcher) { visible: Boolean ->
                SlideVisibility(
                    visible = visible,
                    duration = duration,
                    delay = delay,
                    easing = easing,
                    initialOffset = getOffset,
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
            inDuration = duration,
            inDelay = delay,
            outDuration = duration,
            outDelay = delay,
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
        val duration = .6.seconds
        val delay = .3.seconds
        val easing = Ease
        val getOffset: (fullSize: IntSize) -> IntOffset = { IntOffset(x = it.width, y = 0) }
        rule.setContent {
            Content(switcherTag = switcher) { visible: Boolean ->
                SlideVisibility(
                    visible = visible,
                    modifier = Modifier.testTag(animatedContainer),
                    duration = duration,
                    delay = delay,
                    easing = easing,
                    initialOffset = getOffset,
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
            inDuration = duration,
            inDelay = delay,
            outDuration = duration,
            outDelay = delay,
            onContentReady = {
                rule.onNodeWithTag(animatedContent).assertTextEquals(animatedContent)
            },
        )
    }
}
