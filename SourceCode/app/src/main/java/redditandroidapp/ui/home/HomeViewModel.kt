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

    fun refreshPostsRequested() {
        triggerRedditPostsFetching(null, true)
        stateData = stateData.copy(posts = postsRepository.getCachedPosts())
    }

    fun triggerFreshRedditPostsFetching() {
        //_state.value = State.ContentDisplayedAndLoading(postsRepository.getCachedPosts())
        triggerRedditPostsFetching(null, true)
    }

    fun triggerMoreRedditPostsFetching() {
//        stateData = stateData.copy(posts = postsRepository.getCachedPosts())
//        _state.value = State.ContentDisplayedAndLoading(postsRepository.getCachedPosts())
        triggerRedditPostsFetching(postsRepository.getLastPostName(), false)
    }

    private fun triggerRedditPostsFetching(lastPostId: String?, refreshStoredPosts: Boolean) {
        val callback = this
        stateData = stateData.copy(isLoading = true)
        viewModelScope.launch {
            postsRepository.fetchRedditPosts(
                lastPostId,
                callback,
                refreshStoredPosts
            )
        }
    }

    override fun postsFetchedSuccessfully(list: List<RedditPostModel>) {
        stateData = stateData.copy(posts = list, isLoading = false, errorMessage = null)
    }

    override fun postsFetchingError(
        errorMessage: String,
        cachedRedditPosts: List<RedditPostModel>
    ) {
        if (cachedRedditPosts.isNotEmpty()) {
            stateData = stateData.copy(errorMessage = errorMessage)
        }
    }
}

data class StateData(
    val posts: List<RedditPostModel> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val scrollToTop: Boolean = false
)