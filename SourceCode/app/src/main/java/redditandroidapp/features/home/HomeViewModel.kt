package redditandroidapp.features.home

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
    // Holds our currently selected home category
//    private val selectedCategory = MutableStateFlow(HomeCategory.Discover)
    // Holds the currently available home categories
//    private val fetchedPosts = MutableStateFlow(emptyList())

    // Holds our view state which the UI collects via [state]
    private val _state = MutableStateFlow(HomeViewState())

    private val refreshing = MutableStateFlow(false)

    val state: StateFlow<HomeViewState>
        get() = _state

    init {
        viewModelScope.launch {
//             Combines the latest value from each of the flows, allowing us to generate a
//             view state instance which only contains the latest values.
            combine(
                postsRepository.getRedditPosts_flowApproach(null),
                refreshing
            ) { redditPosts, refreshing ->
                HomeViewState(
                    redditPosts = redditPosts,
                    refreshing = refreshing,
                    errorMessage = null /* TODO */
                )
            }.catch { throwable ->
                // TODO: emit a UI error here. For now we'll just rethrow
                throw throwable
            }.collect {
                _state.value = it
            }
        }

        refresh(force = false)
    }

    private fun refresh(force: Boolean) {
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