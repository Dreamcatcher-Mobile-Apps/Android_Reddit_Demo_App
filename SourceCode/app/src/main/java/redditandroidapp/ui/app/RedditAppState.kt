package redditandroidapp.ui.app

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

/**
 * List of screens for [RedditApp]
 */
sealed class Screen(val route: String) {
    object Home : Screen("home")
}

@Composable
fun rememberJetcasterAppState(
    navController: NavHostController = rememberNavController(),
    context: Context = LocalContext.current
) = remember(navController, context) {
    RedditAndroidAppState(navController)
}

class RedditAndroidAppState(val navController: NavHostController)