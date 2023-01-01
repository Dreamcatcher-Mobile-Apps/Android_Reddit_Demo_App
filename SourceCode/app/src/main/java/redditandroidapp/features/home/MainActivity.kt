package redditandroidapp.features.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import redditandroidapp.app.RedditApp
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