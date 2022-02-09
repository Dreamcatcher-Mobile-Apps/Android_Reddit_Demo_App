package redditandroidapp.injection

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import redditandroidapp.data.database.PostsDatabase
import redditandroidapp.data.database.PostsDatabaseInteractor
import redditandroidapp.data.network.ApiClient
import redditandroidapp.data.network.PostsNetworkInteractor
import redditandroidapp.data.network.NetworkAdapter
import redditandroidapp.data.repositories.PostsRepository
import javax.inject.Singleton

@Module
class FeedModule {

    @Provides
    @Singleton
    fun providesDatabase(application: Context): PostsDatabase {
        return Room.databaseBuilder(application, PostsDatabase::class.java, "main_database").build()
    }

    @Provides
    @Singleton
    fun providesPostsDatabaseInteractor(postsDatabase: PostsDatabase): PostsDatabaseInteractor {
        return PostsDatabaseInteractor(postsDatabase)
    }

    @Provides
    @Singleton
    fun providesPostsNetworkInteractor(apiClient: ApiClient): PostsNetworkInteractor {
        return PostsNetworkInteractor(apiClient)
    }

    @Provides
    @Singleton
    fun providesApiClient(): ApiClient {
        return NetworkAdapter.apiClient()
    }

    @Provides
    @Singleton
    fun providesPostsRepository(postsNetworkInteractor: PostsNetworkInteractor,
                               postsDatabaseInteractor: PostsDatabaseInteractor
    ): PostsRepository {
        return PostsRepository(postsNetworkInteractor, postsDatabaseInteractor)
    }
}