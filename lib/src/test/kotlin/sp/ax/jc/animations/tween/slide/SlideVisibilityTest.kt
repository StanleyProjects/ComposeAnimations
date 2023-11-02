package sp.ax.jc.animations.tween.slide

import androidx.compose.animation.core.Ease
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.time.Duration.Companion.seconds

@RunWith(RobolectricTestRunner::class)
internal class SlideVisibilityTest {
    @get:Rule
    val rule = createComposeRule()

    @Composable
    private fun Content(switcherTag: String, composable: @Composable (visible: Boolean) -> Unit) {
        check(switcherTag.isNotEmpty())
        Box(Modifier.fillMaxSize()) {
            val visibleState = remember { mutableStateOf(false) }
            BasicText(
                modifier = Modifier
                    .testTag(switcherTag)
                    .clickable {
                        visibleState.value = !visibleState.value
                    },
                text = switcherTag,
            )
            composable(visibleState.value)
        }
    }

    @Composable
    private fun AnimatedContent(testTag: String) {
        check(testTag.isNotEmpty())
        BasicText(
            modifier = Modifier
                .testTag(testTag)
                .fillMaxWidth(),
            text = testTag,
        )
    }

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
