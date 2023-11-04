package sp.ax.jc.animations.tween.fade

import androidx.compose.animation.core.Ease
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
        val initialAlpha = .1f
        val targetAlpha = .9f
        rule.setContent {
            Content(switcherTag = switcher) { visible: Boolean ->
                FadeVisibility(
                    visible = visible,
                    inDuration = duration,
                    inDelay = delay,
                    inEasing = easing,
                    initialAlpha = initialAlpha,
                    outDuration = duration,
                    outDelay = delay,
                    outEasing = easing,
                    targetAlpha = targetAlpha,
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
}
