package redditandroidapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import redditandroidapp.R
import redditandroidapp.data.models.RedditPostModel
import redditandroidapp.data.repositories.PostsRepository
import redditandroidapp.injection.RedditAndroidApp
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val postsRepository: PostsRepository): ViewModel() {

    // Holds our view state which the UI collects via [state]
    private val _state = MutableStateFlow(HomeViewState())

    private val fetchingError = MutableStateFlow<Throwable?>(null)

    private val refreshing = MutableStateFlow(false)

    val state: StateFlow<HomeViewState>
        get() = _state

    init {
        viewModelScope.launch {
//             Combines the latest value from each of the flows, allowing us to generate a
//             view state instance which only contains the latest values.
            combine(
                postsRepository.redditPosts,
                fetchingError,
                refreshing
            ) { redditPosts, fetchingError, refreshing ->
                HomeViewState(
                    redditPosts = redditPosts,
                    refreshing = refreshing,
                    errorMessage = getUserFacingErrorMessage(fetchingError)
                )
            }.catch { throwable ->
                fetchingError.emit(throwable)
            }.collect {
                _state.value = it
            }
        }
        triggerFreshRedditPostsFetching()
    }

    // Todo: Implement "refreshing = true"

    fun triggerFreshRedditPostsFetching() {
        triggerRedditPostsFetching(null)
    }

    fun triggerMoreRedditPostsFetching() {
        triggerRedditPostsFetching(postsRepository.getLastPostName())
    }

    private fun triggerRedditPostsFetching(lastPostId: String?) {
        viewModelScope.launch {
            postsRepository.fetchRedditPosts(lastPostId, fetchingError)
        }
    }

    private fun getUserFacingErrorMessage(fetchingError: Throwable?): String {
        val genericErrorMessage = RedditAndroidApp.getLocalResources().getString(R.string.connection_error_message)
        return fetchingError?.message ?: genericErrorMessage
    }
}

data class HomeViewState(
    val redditPosts: List<RedditPostModel>? = null,
    val refreshing: Boolean = false,
    val errorMessage: String? = null
)