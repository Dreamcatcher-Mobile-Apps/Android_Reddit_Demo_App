package redditandroidapp.features.feed

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import redditandroidapp.data.database.RedditPostModel
import redditandroidapp.data.repositories.PostsRepository
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(private val postsRepository: PostsRepository)
    : ViewModel(), LifecycleObserver {

    fun subscribeForPosts(): LiveData<List<RedditPostModel>>? {
        return postsRepository.getAllPosts(null)
    }

    fun refreshPosts() {
        //
    }

//    fun fetchMorePosts(lastPostName: String): LiveData<List<PostDatabaseEntity>>? {
//        return postsRepository.fetchMorePosts(lastPostName)
//    }

//    fun subscribeForUpdateErrors(): LiveData<Boolean>? {
//        return postsRepository.subscribeForUpdateErrors()
//    }
}