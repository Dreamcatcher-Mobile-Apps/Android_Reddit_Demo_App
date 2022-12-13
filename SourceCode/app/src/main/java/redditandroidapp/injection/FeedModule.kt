package redditandroidapp.injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import redditandroidapp.data.network.ApiClient
import redditandroidapp.data.network.NetworkAdapter
import redditandroidapp.data.network.PostsNetworkInteractor
import redditandroidapp.data.repositories.PostsRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class FeedModule {

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
    fun providesPostsRepository(postsNetworkInteractor: PostsNetworkInteractor): PostsRepository {
        return PostsRepository(postsNetworkInteractor)
    }
}