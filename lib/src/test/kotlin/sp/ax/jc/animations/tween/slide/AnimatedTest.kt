package sp.ax.jc.animations.tween.slide

import androidx.compose.animation.core.AnimationConstants
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.SemanticsNode
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertLeftPositionInRootIsEqualTo
import androidx.compose.ui.test.assertPositionInRootIsEqualTo
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import sp.ax.jc.animations.style.LocalTweenStyle
import java.util.Timer
import java.util.TimerTask
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.nanoseconds
import kotlin.time.Duration.Companion.seconds

@RunWith(RobolectricTestRunner::class)
internal class AnimatedTest {
    private val scope = CoroutineScope(Dispatchers.Default + Job())
    @get:Rule
    val rule = createComposeRule()

    private fun ComposeTestRule.wait(duration: Duration, timeout: Duration = duration + 1.seconds) {
        val expired = AtomicBoolean(false)
        scope.launch {
            withContext(Dispatchers.Default) {
                delay(duration)
            }
            expired.set(true)
        }
        waitUntil(timeout.inWholeMilliseconds) {
            expired.get()
        }
    }

    private fun SemanticsNodeInteraction.foo(): String {
        val sn = fetchSemanticsNode()
        val sizes = sn.layoutInfo.getModifierInfo().map { it.coordinates.size }
        return """
            node: ${sn.config}
            parent: ${sn.parent?.config}
            size: ${sn.size}
            coordinates: ${sn.layoutInfo.coordinates.size}
            sizes: $sizes
            boundsInRoot: ${sn.boundsInRoot}
            positionInRoot: ${sn.positionInRoot}
            boundsInWindow: ${sn.boundsInWindow}
            positionInWindow: ${sn.positionInWindow}
        """.trimIndent()
    }

    private fun List<SemanticsNodeInteraction>.foo() {
        val message = joinToString(separator = "\n\n") { it.foo() }
        error(message)
    }

    private fun SemanticsNode.requireParent(): SemanticsNode {
        return parent ?: error("Parent does not exist!")
    }

    private fun SemanticsNode.assertPositionInRootEquals(expected: Offset) {
        assertEquals(expected, positionInRoot)
    }

    private fun SemanticsNode.assertPositionXInRootMoreThan(less: Float) {
        assertTrue(less < positionInRoot.x)
    }

    private fun SemanticsNode.assertPositionXInRootLessThan(more: Float) {
        assertTrue(more > positionInRoot.x)
    }

    @Test
    fun SlideHVisibilityTest() {
        val animatedContainer = "animatedContainer"
        val animatedContent = "animatedContent"
        val switcher = "switcher"
        rule.mainClock.autoAdvance = false
//        val duration = AnimationConstants.DefaultDurationMillis.milliseconds
        val duration = 2.seconds
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
                SlideHVisibility(
                    visible = visible,
                    duration = duration,
                    modifier = Modifier.testTag(animatedContainer),
                ) {
                    BasicText(
                        modifier = Modifier
                            .testTag(animatedContent)
                            .fillMaxWidth()
                            .height(64.dp)
                            .wrapContentSize(),
                        text = animatedContent,
                    )
                }
            }
        }
        rule.onNodeWithTag(animatedContainer).assertDoesNotExist()
        rule.onNodeWithTag(animatedContent).assertDoesNotExist()
        rule.onNodeWithTag(switcher).performClick()
//        rule.mainClock.advanceTimeBy(duration.inWholeMilliseconds / 2)
        rule.mainClock.advanceTimeByFrame()
        rule.onNodeWithTag(animatedContainer).assertExists()
        rule.onNodeWithTag(animatedContent).also { node ->
            node.assertExists()
            node.assertIsNotDisplayed()
            node.fetchSemanticsNode().also {
                assertEquals(it.positionInRoot, Offset(x = it.requireParent().size.width.toFloat(), y = 0f))
            }
//            error(node.foo())
            rule.mainClock.advanceTimeBy(duration.inWholeMilliseconds)
//            error(node.foo())
            node.assertIsDisplayed()
            node.fetchSemanticsNode().assertPositionInRootEquals(Offset.Zero)
            node.assertTextEquals(animatedContent)
            node.assert(hasParent(hasTestTag(animatedContainer)))
        }
//        rule.wait(duration)
        val timeStart = System.nanoTime().nanoseconds
        rule.onNodeWithTag(switcher).performClick()
        rule.onNodeWithTag(animatedContainer).assertExists()
        rule.onNodeWithTag(animatedContent).also { node ->
            node.assertExists()
            node.assertIsDisplayed()
            node.fetchSemanticsNode().assertPositionInRootEquals(Offset.Zero)
        }
        rule.mainClock.advanceTimeBy(duration.inWholeMilliseconds / 2)
        rule.onNodeWithTag(animatedContainer).assertExists()
        rule.onNodeWithTag(animatedContent).also { node ->
            node.assertExists()
            node.assertIsNotDisplayed()
            node.fetchSemanticsNode().also {
                assertTrue(it.positionInRoot.x > 0)
                assertTrue(it.positionInRoot.x < it.requireParent().size.width.toFloat())
            }
        }
        rule.mainClock.advanceTimeBy(duration.inWholeMilliseconds / 2)
        rule.onNodeWithTag(animatedContainer).assertExists()
        rule.onNodeWithTag(animatedContent).also { node ->
            node.assertExists()
            node.assertIsNotDisplayed()
            node.fetchSemanticsNode().also {
                assertEquals(it.positionInRoot.x, it.requireParent().size.width.toFloat())
            }
        }
        rule.mainClock.advanceTimeByFrame()
        rule.onNodeWithTag(animatedContainer).assertDoesNotExist()
        rule.onNodeWithTag(animatedContent).assertDoesNotExist()
//        rule.waitUntil(5_000) {
//            rule.onAllNodes(hasTestTag("animatedContainer")).fetchSemanticsNodes().size == 1
//        }
    }
}
