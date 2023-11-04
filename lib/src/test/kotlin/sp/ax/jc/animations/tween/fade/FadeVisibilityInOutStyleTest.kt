package sp.ax.jc.animations.tween.fade

import androidx.compose.animation.core.Ease
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
import kotlin.time.Duration.Companion.seconds

@RunWith(RobolectricTestRunner::class)
internal class FadeVisibilityInOutStyleTest : AnimatedTest() {
    @Test
    fun defaultAutoAdvanceTest() {
        val animatedContent = "animatedContent"
        val switcher = "switcher"
        val duration = .6.seconds
        val delay = .3.seconds
        val easing = Ease
        val alpha = 0f
        rule.setContent {
            Content(switcherTag = switcher) { visible: Boolean ->
                FadeVisibility(
                    visible = visible,
                    inDuration = duration,
                    inDelay = delay,
                    inEasing = easing,
                    initialAlpha = alpha,
                    outDuration = duration,
                    outDelay = delay,
                    outEasing = easing,
                    targetAlpha = alpha,
                ) {
                    AnimatedContent(testTag = animatedContent)
                }
            }
        }
        rule.onNodeWithTag(animatedContent).assertDoesNotExist()
        rule.onNodeWithTag(switcher).performClick()
        rule.onNodeWithTag(animatedContent).assertIsDisplayed()
        rule.onNodeWithTag(animatedContent).assertTextEquals(animatedContent)
        rule.onNodeWithTag(switcher).performClick()
        rule.onNodeWithTag(animatedContent).assertDoesNotExist()
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
        val initialAlpha = .1f
        val targetAlpha = .9f
        rule.setContent {
            Content(switcherTag = switcher) { visible: Boolean ->
                FadeVisibility(
                    visible = visible,
                    inDuration = inDuration,
                    inDelay = inDelay,
                    inEasing = easing,
                    initialAlpha = initialAlpha,
                    outDuration = outDuration,
                    outDelay = outDelay,
                    outEasing = easing,
                    targetAlpha = targetAlpha,
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
            inDelay = inDelay,
            inDuration = inDuration,
            onContentReady = {
                rule.onNodeWithTag(animatedContent).assertTextEquals(animatedContent)
            },
            performFinishToStart = {
                rule.onNodeWithTag(switcher).performClick()
            },
            outDelay = outDelay,
            outDuration = outDuration,
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
        val initialAlpha = .1f
        val targetAlpha = .9f
        rule.setContent {
            Content(switcherTag = switcher) { visible: Boolean ->
                FadeVisibility(
                    visible = visible,
                    label = label,
                    inDuration = inDuration,
                    inDelay = inDelay,
                    inEasing = easing,
                    initialAlpha = initialAlpha,
                    outDuration = outDuration,
                    outDelay = outDelay,
                    outEasing = easing,
                    targetAlpha = targetAlpha,
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
        val initialAlpha = .1f
        val targetAlpha = .9f
        rule.setContent {
            Content(switcherTag = switcher) { visible: Boolean ->
                FadeVisibility(
                    visible = visible,
                    modifier = Modifier.testTag(animatedContainer),
                    inDuration = inDuration,
                    inDelay = inDelay,
                    inEasing = easing,
                    initialAlpha = initialAlpha,
                    outDuration = outDuration,
                    outDelay = outDelay,
                    outEasing = easing,
                    targetAlpha = targetAlpha,
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
            inDelay = inDelay,
            inDuration = inDuration,
            onContentReady = {
                rule.onNodeWithTag(animatedContent).assertTextEquals(animatedContent)
            },
            performFinishToStart = {
                rule.onNodeWithTag(switcher).performClick()
            },
            outDelay = outDelay,
            outDuration = outDuration,
        )
    }
}
