package sp.ax.jc.animations.tween.slide

import androidx.compose.animation.core.AnimationConstants
import androidx.compose.animation.core.Ease
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import sp.ax.jc.animations.style.LocalSlideHStyle
import sp.ax.jc.animations.style.LocalTweenStyle
import sp.ax.jc.animations.style.SlideStyle
import sp.ax.jc.animations.style.TweenStyle
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

@RunWith(RobolectricTestRunner::class)
internal class SlideHVisibilityTest : AnimatedTest() {
    @Test
    fun defaultAutoAdvanceTest() {
        val animatedContent = "animatedContent"
        val switcher = "switcher"
        rule.setContent {
            Content(switcherTag = switcher) { visible: Boolean ->
                SlideHVisibility(visible = visible) {
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
        val duration = AnimationConstants.DefaultDurationMillis.milliseconds
        val delay = Duration.ZERO
        rule.setContent {
            Content(switcherTag = switcher) { visible: Boolean ->
                SlideHVisibility(visible = visible) {
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
                SlideHVisibility(
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

    @Test
    fun styleTest() {
        val animatedContent = "animatedContent"
        val switcher = "switcher"
        val duration = .6.seconds
        val delay = .3.seconds
        val easing = Ease
        rule.setContent {
            Content(switcherTag = switcher) { visible: Boolean ->
                SlideHVisibility(
                    visible = visible,
                    duration = duration,
                    delay = delay,
                    easing = easing,
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
    fun styleCompositionLocalTest() {
        val animatedContent = "animatedContent"
        val switcher = "switcher"
        val duration = 2.seconds
        val delay = 1.seconds
        val easing = Ease
        rule.setContent {
            CompositionLocalProvider(
                LocalTweenStyle provides TweenStyle(
                    duration = duration,
                    delay = delay,
                    easing = easing,
                ),
            ) {
                Content(switcherTag = switcher) { visible: Boolean ->
                    SlideHVisibility(visible = visible) {
                        AnimatedContent(testTag = animatedContent)
                    }
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
    fun toRightToRightTest() {
        val animatedContent = "animatedContent"
        val switcher = "switcher"
        val duration = AnimationConstants.DefaultDurationMillis.milliseconds
        val delay = Duration.ZERO
        val initialOffsetX: (fullWidth: Int) -> Int = { -it }
        val targetOffsetX: (fullWidth: Int) -> Int = { it }
        rule.setContent {
            Content(switcherTag = switcher) { visible: Boolean ->
                SlideHVisibility(
                    visible = visible,
                    initialOffsetX = initialOffsetX,
                    targetOffsetX = targetOffsetX,
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
            expectedOffsetOnInitial = {
                Offset(x = -it.width.toFloat(), y = 0f)
            },
            expectedOffsetOnTarget = {
                Offset(x = it.width.toFloat(), y = 0f)
            },
            offsetXRangeInTheMiddleInitial = {
                -it.width.toFloat() to 0f
            },
            offsetXRangeInTheMiddleTarget = {
                0f to it.width.toFloat()
            },
        )
    }

    @Test
    fun toRightToRightCompositionLocalTest() {
        val animatedContent = "animatedContent"
        val switcher = "switcher"
        val duration = AnimationConstants.DefaultDurationMillis.milliseconds
        val delay = Duration.ZERO
        val initialOffsetX: (fullWidth: Int) -> Int = { -it }
        val targetOffsetX: (fullWidth: Int) -> Int = { it }
        rule.setContent {
            CompositionLocalProvider(
                LocalSlideHStyle provides SlideStyle.Horizontal(
                    initialOffsetX = initialOffsetX,
                    targetOffsetX = targetOffsetX,
                ),
            ) {
                Content(switcherTag = switcher) { visible: Boolean ->
                    SlideHVisibility(visible = visible) {
                        AnimatedContent(testTag = animatedContent)
                    }
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
            expectedOffsetOnInitial = {
                Offset(x = -it.width.toFloat(), y = 0f)
            },
            expectedOffsetOnTarget = {
                Offset(x = it.width.toFloat(), y = 0f)
            },
            offsetXRangeInTheMiddleInitial = {
                -it.width.toFloat() to 0f
            },
            offsetXRangeInTheMiddleTarget = {
                0f to it.width.toFloat()
            },
        )
    }
}
