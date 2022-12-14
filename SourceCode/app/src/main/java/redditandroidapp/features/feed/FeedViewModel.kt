package redditandroidapp.features.feed

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import redditandroidapp.data.repositories.PostsRepository
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(private val postsRepository: PostsRepository)
    : ViewModel(), LifecycleObserver {

    fun subscribeForPosts(callback: RedditPostsFetchingInterface) {
        return postsRepository.getAllPosts(callback, null)
    }

    fun refreshPosts(callback: RedditPostsFetchingInterface) {
        return postsRepository.getAllPosts(callback, null)
    }

//    fun fetchMorePosts(lastPostName: String): LiveData<List<PostDatabaseEntity>>? {
//        return postsRepository.fetchMorePosts(lastPostName)
//    }
}