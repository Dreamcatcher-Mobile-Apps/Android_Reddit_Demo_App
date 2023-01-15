package redditandroidapp.ui.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import redditandroidapp.ui.home.Home

@Composable
fun RedditApp(
    appState: RedditAndroidAppState = rememberJetcasterAppState()
) {
    NavHost(
        navController = appState.navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) { Home() }
    }
}