package redditandroidapp.injection

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import redditandroidapp.features.detailedview.DetailedViewViewModel
import redditandroidapp.features.feed.FeedViewModel

@InstallIn(SingletonComponent::class)
@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindFeedViewModel(feedViewModel: FeedViewModel)
            : ViewModel

    @Binds
    internal abstract fun bindDetailedViewViewModel(detailedViewViewModel: DetailedViewViewModel)
            : ViewModel
}