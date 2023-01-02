package redditandroidapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import redditandroidapp.ui.app.RedditApp
import redditandroidapp.ui.theme.SourceCodeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SourceCodeTheme {
                RedditApp()
            }
        }
    }
}