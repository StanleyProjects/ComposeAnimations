package sp.sample.animations

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import sp.ax.jc.animations.tween.slide.SlideHVisibility

internal class MainActivity : AppCompatActivity() {
    override fun onCreate(inState: Bundle?) {
        super.onCreate(inState)
        setContent {
            val insets = LocalView.current.rootWindowInsets.toPaddings()
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(insets),
            ) {
                val visibleState = remember { mutableStateOf(false) }
                BasicText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .align(Alignment.BottomCenter)
                        .clickable {
                            visibleState.value = !visibleState.value
                        }
                        .wrapContentSize(),
                    text = if (visibleState.value) "hide" else "show",
                )
                SlideHVisibility(
                    visible = visibleState.value,
                    modifier = Modifier.align(Alignment.Center),
                ) {
                    BasicText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                            .background(Color.Gray)
                            .wrapContentSize(),
                        text = BuildConfig.APPLICATION_ID,
                    )
                }
            }
        }
    }
}
