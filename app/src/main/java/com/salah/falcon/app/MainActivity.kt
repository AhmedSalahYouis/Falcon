package com.salah.falcon.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.salah.falcon.core.data.util.NetworkMonitor
import com.salah.falcon.core.designsystem.theme.FalconTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val networkMonitor: NetworkMonitor by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appState = rememberFalconAppState(networkMonitor = networkMonitor)

            FalconTheme {
                FalconComposeApp(appState)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FalconTheme {
        Greeting("Android")
    }
}