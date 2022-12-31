package redditandroidapp.features.feed

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import redditandroidapp.data.repositories.PostsRepository
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(private val postsRepository: PostsRepository)
    : ViewModel(), LifecycleObserver {

    fun fetchRedditPostsFromServer(callback: RedditPostsFetchingInterface) {
        postsRepository.getRedditPosts(callback, null, false)
    }

    fun fetchRefreshedRedditPostsFromServer(callback: RedditPostsFetchingInterface) {
        postsRepository.getRedditPosts(callback, null, true)
    }

    fun fetchMoreRedditPostsFromServer(callback: RedditPostsFetchingInterface, lastPostName: String) {
        postsRepository.getRedditPosts(callback, lastPostName, false)
    }
}