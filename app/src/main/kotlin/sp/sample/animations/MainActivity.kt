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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import sp.ax.jc.animations.tween.fade.FadeVisibility
import sp.ax.jc.animations.tween.slide.SlideHVisibility

internal class MainActivity : AppCompatActivity() {
    @Composable
    private fun Text(
        background: Color,
        text: String,
    ) {
        BasicText(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(background)
                .wrapContentSize(),
            text = text,
        )
    }

    @Composable
    private fun Button(
        text: String,
        onClick: () -> Unit,
    ) {
        BasicText(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .clickable(onClick = onClick)
                .wrapContentSize(),
            text = text,
        )
    }

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
                val visibleSlideHState = remember { mutableStateOf(false) }
                val visibleFadeState = remember { mutableStateOf(false) }
                Column(Modifier.fillMaxWidth()) {
                    SlideHVisibility(visible = visibleSlideHState.value) {
                        Text(
                            background = Color.Gray,
                            text = "slide horizontally",
                        )
                    }
                    FadeVisibility(visible = visibleFadeState.value) {
                        Text(
                            background = Color.Green,
                            text = "fade",
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                ) {
                    Button(
                        text = if (visibleSlideHState.value) "slide H hide" else "slide H show",
                        onClick = {
                            visibleSlideHState.value = !visibleSlideHState.value
                        },
                    )
                    Button(
                        text = if (visibleFadeState.value) "fade hide" else "fade show",
                        onClick = {
                            visibleFadeState.value = !visibleFadeState.value
                        },
                    )
                }
            }
        }
    }
}
