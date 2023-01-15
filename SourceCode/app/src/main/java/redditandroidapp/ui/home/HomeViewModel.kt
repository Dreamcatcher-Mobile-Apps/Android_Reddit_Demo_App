package redditandroidapp.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import redditandroidapp.data.models.RedditPostModel
import redditandroidapp.data.repositories.PostsRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val postsRepository: PostsRepository) : ViewModel(),
    PostsFetchingCallback {

    var stateData by mutableStateOf(StateData())

    init {
        triggerFreshRedditPostsFetching()
    }

    private fun triggerFreshRedditPostsFetching() {
        val callback = this
        stateData = stateData.copy(isLoading = true)
        viewModelScope.launch {
            postsRepository.fetchFreshRedditPosts(callback)
        }
    }

    fun refreshPostsRequested() {
        val callback = this
        stateData = stateData.copy(isLoading = true)
        viewModelScope.launch {
            postsRepository.fetchFreshRedditPosts(callback)
        }
    }

    fun fetchMorePostsRequested() {
        val callback = this
        stateData = stateData.copy(isLoading = true)
        viewModelScope.launch {
            val lastPostId = postsRepository.getLastPostName()
            if (lastPostId != null) {
                postsRepository.fetchMoreRedditPosts(lastPostId, callback)
            } else {
                // Todo
            }
        }
    }

    override fun cachedPostsReadyForDisplay(cachedRedditPosts: List<RedditPostModel>) {
//        stateData = stateData.copy(posts = cachedRedditPosts)
    }

    override fun postsFetchedSuccessfully(list: List<RedditPostModel>) {
        stateData = stateData.copy(posts = list, isLoading = false, errorMessage = null)
    }

    override fun postsFetchingError(errorMessage: String) {
        // A bug. When we open app without internet, the errorMessage goes through, but the isLoading doesnt!
        stateData = stateData.copy(isLoading = false, errorMessage = errorMessage)
    }
}

data class StateData(
    val posts: List<RedditPostModel> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val scrollToTop: Boolean = false
)