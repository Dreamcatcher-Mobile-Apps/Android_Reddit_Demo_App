package redditandroidapp.ui.home

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

    init {
        triggerFreshRedditPostsFetching()
    }

    fun triggerFreshRedditPostsFetching() {
        triggerRedditPostsFetching(null, true)
    }

    fun triggerMoreRedditPostsFetching() {
        _state.value = State.ContentDisplayedAndRefreshing(postsRepository.getCachedPosts())
        triggerRedditPostsFetching(postsRepository.getLastPostName(), false)
    }

    private fun triggerRedditPostsFetching(lastPostId: String?, refreshStoredPosts: Boolean) {
        val callback = this
        viewModelScope.launch {
            postsRepository.fetchRedditPosts(
                lastPostId,
                callback,
                refreshStoredPosts
            )
        }
    }

    override fun postsFetchedSuccessfully(list: List<RedditPostModel>) {
        _state.value = State.ContentDisplayedSuccessfully(list)
    }

    override fun postsFetchingError(
        errorMessage: String,
        cachedRedditPosts: List<RedditPostModel>
    ) {
        if (cachedRedditPosts.isNotEmpty()) _state.value =
            State.ContentDisplayedAndRefreshingError(cachedRedditPosts, errorMessage)
        else _state.value = State.InitialLoadingError(errorMessage)
    }
}

sealed class State {
    object InitialLoading : State()
    data class InitialLoadingError(val errorMessage: String) : State()
    data class ContentDisplayedSuccessfully(val posts: List<RedditPostModel>) : State()
    data class ContentDisplayedAndRefreshing(val posts: List<RedditPostModel>) : State()
    data class ContentDisplayedAndRefreshingError(
        val posts: List<RedditPostModel>,
        val errorMessage: String
    ) : State()
}