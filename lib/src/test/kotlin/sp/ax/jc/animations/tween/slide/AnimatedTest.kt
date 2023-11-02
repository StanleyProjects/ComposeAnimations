package sp.ax.jc.animations.tween.slide

import androidx.compose.animation.core.AnimationConstants
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import sp.ax.jc.animations.style.LocalTweenStyle
import sp.ax.jc.animations.style.TweenStyle
import sp.ax.jc.animations.tween.slide.util.compose.assertAnimation
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

@RunWith(RobolectricTestRunner::class)
internal class AnimatedTest {
    @get:Rule
    val rule = createComposeRule()

    @Composable
    private fun Content(switcherTag: String, composable: @Composable (visible: Boolean) -> Unit) {
        Box(Modifier.fillMaxSize()) {
            val visibleState = remember { mutableStateOf(false) }
            BasicText(
                modifier = Modifier
                    .testTag(switcherTag)
                    .fillMaxWidth()
                    .height(64.dp)
                    .align(Alignment.BottomCenter)
                    .clickable {
                        visibleState.value = !visibleState.value
                    }
                    .wrapContentSize(),
                text = switcherTag,
            )
            composable(visibleState.value)
        }
    }

    @Composable
    private fun AnimatedContent(testTag: String) {
        BasicText(
            modifier = Modifier
                .testTag(testTag)
                .fillMaxWidth()
                .height(64.dp)
                .wrapContentSize(),
            text = testTag,
        )
    }

    private fun assertAnimation(
        containerTag: String,
        contentTag: String,
        switcherTag: String,
        duration: Duration,
    ) {
        assertAnimation(
            provider = rule,
            mainClock = rule.mainClock,
            containerTag = containerTag,
            contentTag = contentTag,
            performStartToFinish = {
                rule.onNodeWithTag(switcherTag).performClick()
            },
            performFinishToStart = {
                rule.onNodeWithTag(switcherTag).performClick()
            },
            duration = duration,
            onContentReady = {
                rule.onNodeWithTag(contentTag).assertTextEquals(contentTag)
            },
        )
    }

    @Test
    fun SlideHVisibilityDefaultAutoAdvanceTest() {
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
    fun SlideHVisibilityDefaultTest() {
        val animatedContainer = "animatedContainer"
        val animatedContent = "animatedContent"
        val switcher = "switcher"
        rule.mainClock.autoAdvance = false
        val duration = AnimationConstants.DefaultDurationMillis.milliseconds
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
        assertAnimation(
            containerTag = animatedContainer,
            contentTag = animatedContent,
            switcherTag = switcher,
            duration = duration,
        )
    }

    @Test
    fun SlideHVisibilityCustomDurationTest() {
        val animatedContainer = "animatedContainer"
        val animatedContent = "animatedContent"
        val switcher = "switcher"
        rule.mainClock.autoAdvance = false
        val duration = 2.seconds
        rule.setContent {
            Content(switcherTag = switcher) { visible: Boolean ->
                SlideHVisibility(
                    visible = visible,
                    modifier = Modifier.testTag(animatedContainer),
                    duration = duration,
                ) {
                    AnimatedContent(testTag = animatedContent)
                }
            }
        }
        assertAnimation(
            containerTag = animatedContainer,
            contentTag = animatedContent,
            switcherTag = switcher,
            duration = duration,
        )
    }

    @Test
    fun SlideHVisibilityCustomCompositionTest() {
        val animatedContainer = "animatedContainer"
        val animatedContent = "animatedContent"
        val switcher = "switcher"
        rule.mainClock.autoAdvance = false
        val duration = 2.seconds
        rule.setContent {
            CompositionLocalProvider(
                LocalTweenStyle provides TweenStyle(
                    duration = duration,
                    delay = Duration.ZERO,
                    easing = FastOutSlowInEasing,
                )
            ) {
                Content(switcherTag = switcher) { visible: Boolean ->
                    SlideHVisibility(
                        visible = visible,
                        modifier = Modifier.testTag(animatedContainer),
                    ) {
                        AnimatedContent(testTag = animatedContent)
                    }
                }
            }
        }
        assertAnimation(
            containerTag = animatedContainer,
            contentTag = animatedContent,
            switcherTag = switcher,
            duration = duration,
        )
    }
}
