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
        triggerRepositoryToFetchRedditPosts {
            postsRepository.fetchFreshRedditPosts(this)
        }
    }

    fun refreshPostsRequested() {
        triggerRepositoryToFetchRedditPosts {
            postsRepository.fetchFreshRedditPosts(this)
        }
    }

    fun fetchMorePostsRequested() {
        triggerRepositoryToFetchRedditPosts {
            val lastPostId = postsRepository.getLastPostName()
            if (lastPostId != null) {
                postsRepository.fetchMoreRedditPosts(lastPostId, this)
            } else {
                // Todo: throw exception or error.
            }
        }
    }

    private fun triggerRepositoryToFetchRedditPosts(request: () -> Unit) {
        stateData = stateData.copy(isLoading = true)
        viewModelScope.launch {
            request.invoke()
        }
    }

    override fun postsFetchedSuccessfully(list: List<RedditPostModel>) {
        stateData = stateData.copy(posts = list, isLoading = false, errorMessage = null)
    }

    override fun postsFetchingError(errorMessage: String) {
        // A bug. When we open app without internet, the errorMessage goes through, but the isLoading doesnt!
        // Reason: fetchMorePostsRequested is triggered when  we have no posts displayed.
        stateData = stateData.copy(isLoading = false, errorMessage = errorMessage)
    }
}

data class StateData(
    val posts: List<RedditPostModel> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val scrollToTop: Boolean = false
)