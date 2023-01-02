package redditandroidapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import redditandroidapp.ui.app.RedditApp
import redditandroidapp.ui.theme.SourceCodeTheme

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