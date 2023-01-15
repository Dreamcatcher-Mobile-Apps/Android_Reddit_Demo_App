package redditandroidapp.injection

import android.app.Application
import android.content.res.Resources
import dagger.hilt.android.HiltAndroidApp

// Todo: Is it possible to unify this file with RedditApp.kt?

@HiltAndroidApp
class RedditAndroidApp : Application() {

    override fun onCreate() {
        super.onCreate()
        localResources = resources
    }

    companion object {
        private lateinit var localResources: Resources

        fun getLocalResources(): Resources {
            return localResources
        }
    }
}