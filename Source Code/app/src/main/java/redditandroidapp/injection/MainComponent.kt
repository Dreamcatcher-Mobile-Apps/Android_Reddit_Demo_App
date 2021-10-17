package redditandroidapp.injection

import dagger.Component
import redditandroidapp.data.database.PostsDatabaseInteractor
import redditandroidapp.data.network.PostsNetworkInteractor
import redditandroidapp.features.detailedview.DetailedViewFragment
import redditandroidapp.features.detailedview.DetailedViewViewModel
import redditandroidapp.features.feed.FeedActivity
import redditandroidapp.features.feed.FeedViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, FeedModule::class, ViewModelModule::class))
interface MainComponent {
    fun inject(feedActivity: FeedActivity)
    fun inject(detailedViewFragment: DetailedViewFragment)
    fun inject(feedViewModel: FeedViewModel)
    fun inject(detailedViewViewModel: DetailedViewViewModel)
    fun inject(postsNetworkInteractor: PostsNetworkInteractor)
    fun inject(postsDatabaseInteractor: PostsDatabaseInteractor)
}