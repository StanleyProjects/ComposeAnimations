package sp.ax.jc.animations.tween.slide

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class AnimatedTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun slideHorizontallyTest() {
        val animated = "animated"
        val switcher = "switcher"
        rule.setContent {
            Box(Modifier.fillMaxSize()) {
                var visible by remember { mutableStateOf(false) }
                BasicText(
                    modifier = Modifier
                        .testTag(switcher)
                        .fillMaxWidth()
                        .height(64.dp)
                        .align(Alignment.BottomCenter)
                        .clickable {
                            visible = !visible
                        }
                        .wrapContentSize(),
                    text = switcher,
                )
                SlideHorizontally(
                    visible = visible,
                    modifier = Modifier.align(Alignment.Center),
                ) {
                    BasicText(
                        modifier = Modifier
                            .testTag(animated)
                            .fillMaxWidth()
                            .height(64.dp)
                            .wrapContentSize(),
                        text = animated,
                    )
                }
            }
        }
        rule.onNodeWithTag(animated).assertDoesNotExist()
        rule.onNodeWithTag(switcher).performClick()
        rule.onNodeWithTag(animated).assertIsDisplayed()
        rule.onNodeWithTag(switcher).performClick()
        rule.onNodeWithTag(animated).assertDoesNotExist()
    }
}
