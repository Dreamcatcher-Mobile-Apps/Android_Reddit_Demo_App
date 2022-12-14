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
        return postsRepository.getRedditPosts(callback, null, false)
    }

    fun fetchRefreshedRedditPostsFromServer(callback: RedditPostsFetchingInterface) {
        return postsRepository.getRedditPosts(callback, null, true)
    }

    fun fetchMoreRedditPostsFromServer(callback: RedditPostsFetchingInterface, lastPostName: String) {
        return postsRepository.getRedditPosts(callback, lastPostName, false)
    }
}