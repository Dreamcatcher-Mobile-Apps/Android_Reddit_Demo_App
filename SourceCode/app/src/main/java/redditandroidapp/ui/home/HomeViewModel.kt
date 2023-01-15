package redditandroidapp.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import redditandroidapp.data.models.RedditPostModel
import redditandroidapp.data.repositories.PostsRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val postsRepository: PostsRepository) : ViewModel(),
    PostsFetchingCallback {

    // Holds our view state which the UI collects via [state]
    private val _state = MutableStateFlow<State>(State.InitialLoading)

    val state: StateFlow<State>
        get() = _state

    var stateData by mutableStateOf(StateData())

    init {
        triggerFreshRedditPostsFetching()
    }

    fun refreshPostsRequested() {
        _state.value = State.ContentDisplayedAndLoading(postsRepository.getCachedPosts())
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
        _state.value = State.ContentDisplayedSuccessfully(list)
    }

    override fun postsFetchingError(
        errorMessage: String,
        cachedRedditPosts: List<RedditPostModel>
    ) {
        if (cachedRedditPosts.isNotEmpty()) {
            stateData = stateData.copy(errorMessage = errorMessage)
            _state.value =
                State.ContentDisplayedAndLoadingError(cachedRedditPosts, errorMessage)
        }
        else _state.value = State.InitialLoadingError(errorMessage)
    }
}

data class StateData(
    val posts: List<RedditPostModel> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val scrollToTop: Boolean = false
)

sealed class State {
    object InitialLoading : State()
    data class InitialLoadingError(val errorMessage: String) : State()
    data class ContentDisplayedSuccessfully(val posts: List<RedditPostModel>) : State()
    data class ContentDisplayedAndLoading(val posts: List<RedditPostModel>) : State()
    data class ContentDisplayedAndLoadingError(
        val posts: List<RedditPostModel>,
        val errorMessage: String
    ) : State()
}