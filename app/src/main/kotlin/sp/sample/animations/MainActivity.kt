package sp.sample.animations

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

internal class MainActivity : AppCompatActivity() {
    override fun onCreate(inState: Bundle?) {
        super.onCreate(inState)
        setContent {
            Box(Modifier.fillMaxSize()) {
                BasicText(
                    modifier = Modifier.align(Alignment.Center),
                    text = BuildConfig.APPLICATION_ID,
                )
            }
        }
    }
}
