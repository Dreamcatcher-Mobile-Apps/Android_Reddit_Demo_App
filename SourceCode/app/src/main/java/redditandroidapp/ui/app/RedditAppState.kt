package redditandroidapp.ui.app

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.getSystemService
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
    RedditAndroidAppState(navController, context)
}

class RedditAndroidAppState(
    val navController: NavHostController,
    private val context: Context
) {
    var isOnline by mutableStateOf(checkIfOnline())
        private set

    fun refreshOnline() {
        isOnline = checkIfOnline()
    }

    // TODO: deprecated code
    @Suppress("DEPRECATION")
    private fun checkIfOnline(): Boolean {
        val cm = getSystemService(context, ConnectivityManager::class.java)

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val capabilities = cm?.getNetworkCapabilities(cm.activeNetwork) ?: return false
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        } else {
            cm?.activeNetworkInfo?.isConnectedOrConnecting == true
        }
    }
}