package redditandroidapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import redditandroidapp.data.models.RedditPostModel
import redditandroidapp.data.repositories.PostsRepository
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
                postsRepository.getRedditPosts(null),
                fetchingError,
                refreshing
            ) { redditPosts, fetchingError, refreshing ->
                // Todo: Generic error message & improve.
                val errorMessage = if (fetchingError != null) { fetchingError.message ?: "ERROR" } else null
                HomeViewState(
                    redditPosts = redditPosts,
                    refreshing = refreshing,
                    errorMessage = errorMessage
                )
            }.catch { throwable ->
                fetchingError.emit(throwable)
            }.collect {
                _state.value = it
            }
        }
    }

    // Todo
    fun refresh() {
        viewModelScope.launch {
            runCatching {
                refreshing.value = true
//                podcastsRepository.updatePodcasts(force)
            }
            // TODO: look at result of runCatching and show any errors

            refreshing.value = false
        }
    }
}

data class HomeViewState(
    val redditPosts: List<RedditPostModel>? = null,
    val refreshing: Boolean = false,
    val errorMessage: String? = null
)