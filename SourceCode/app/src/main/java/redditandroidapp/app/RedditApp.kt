package redditandroidapp.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import redditandroidapp.features.home.Home

@Composable
fun RedditApp(
    appState: JetcasterAppState = rememberJetcasterAppState()
) {
    if (appState.isOnline) {
        NavHost(
            navController = appState.navController,
            startDestination = Screen.Home.route
        ) {
            composable(Screen.Home.route) { backStackEntry -> Home() }
        }
    } else {
        OfflineDialog { appState.refreshOnline() }
    }
}

@Composable
fun OfflineDialog(onRetry: () -> Unit) {
//    AlertDialog(
//        onDismissRequest = {},
//        title = { Text(text = stringResource(R.string.connection_error_title)) },
//        text = { Text(text = stringResource(R.string.connection_error_message)) },
//        confirmButton = {
//            TextButton(onClick = onRetry) {
//                Text(stringResource(R.string.retry_label))
//            }
//        }
//    )
}