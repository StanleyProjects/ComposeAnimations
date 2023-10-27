package sp.sample.animations

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import sp.ax.jc.animations.AnimatedFadeVisibility
import kotlin.time.Duration.Companion.seconds

internal class MainActivity : AppCompatActivity() {
    override fun onCreate(inState: Bundle?) {
        super.onCreate(inState)
        setContent {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(vertical = 48.dp),
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
                AnimatedFadeVisibility(
                    visible = visibleState.value,
                    modifier = Modifier.align(Alignment.Center),
                    duration = 0.5.seconds,
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
