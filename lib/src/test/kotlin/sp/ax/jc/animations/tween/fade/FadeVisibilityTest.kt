package sp.ax.jc.animations.tween.fade

import androidx.compose.animation.core.AnimationConstants
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import sp.ax.jc.animations.util.AnimatedTest
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

@RunWith(RobolectricTestRunner::class)
internal class FadeVisibilityTest : AnimatedTest() {
    @Test
    fun defaultAutoAdvanceTest() {
        val animatedContent = "animatedContent"
        val switcher = "switcher"
        rule.setContent {
            Content(switcherTag = switcher) { visible: Boolean ->
                FadeVisibility(visible = visible) {
                    AnimatedContent(testTag = animatedContent)
                }
            }
        }
        rule.onNodeWithTag(animatedContent).assertDoesNotExist()
        rule.onNodeWithTag(switcher).performClick()
        rule.onNodeWithTag(animatedContent)
            .assertIsDisplayed()
            .assertTextEquals(animatedContent)
        rule.onNodeWithTag(switcher).performClick()
        rule.onNodeWithTag(animatedContent).assertDoesNotExist()
    }

    @Test
    fun defaultTest() {
        val animatedContent = "animatedContent"
        val switcher = "switcher"
        val duration = AnimationConstants.DefaultDurationMillis.milliseconds
        val delay = Duration.ZERO
        rule.setContent {
            Content(switcherTag = switcher) { visible: Boolean ->
                FadeVisibility(visible = visible) {
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
        val duration = AnimationConstants.DefaultDurationMillis.milliseconds
        val delay = Duration.ZERO
        rule.setContent {
            Content(switcherTag = switcher) { visible: Boolean ->
                FadeVisibility(
                    visible = visible,
                    modifier = Modifier.testTag(animatedContainer),
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
