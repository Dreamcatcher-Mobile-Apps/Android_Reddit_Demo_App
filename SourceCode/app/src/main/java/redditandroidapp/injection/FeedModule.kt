package redditandroidapp.injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import redditandroidapp.data.network.ApiClient
import redditandroidapp.data.network.NetworkAdapter
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class FeedModule {

    @Provides
    @Singleton
    fun providesApiClient(): ApiClient {
        return NetworkAdapter.apiClient()
    }
}