package redditandroidapp.features.feed

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.loading_badge.*
import redditandroidapp.R
import redditandroidapp.data.models.RedditPostModel

@AndroidEntryPoint
class MainActivityOld : ComponentActivity(), RedditPostsFetchingInterface  {

    private var isLoadingMoreItemsInProgress: Boolean = false

    private val STATE_INITIAL_LOADING_IN_PROGRESS = "STATE_INITIAL_LOADING_IN_PROGRESS"
    private val STATE_INITIAL_LOADING_ERROR = "STATE_INITIAL_LOADING_ERROR"
    private val STATE_LIST_CONTENT_LOADED_SUCCESSFULLY = "STATE_LIST_CONTENT_LOADED_SUCCESSFULLY"
    private val STATE_ERROR_OCCURS_WHEN_LIST_CONTENT_IS_LOADED =
        "STATE_ERROR_OCCURS_WHEN_LIST_CONTENT_IS_LOADED"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize UI
        setViewState(STATE_INITIAL_LOADING_IN_PROGRESS)

        // Fetch feed items from the back-end and load them into the view
        fetchRedditPostsFromServer()
    }

    private fun fetchRedditPostsFromServer() {
//        viewModel.fetchRedditPostsFromServer(this)
    }

    private fun refreshPostsSubscription() {
//        viewModel.fetchRefreshedRedditPostsFromServer(this)
    }

//    private fun loadMoreItems() {
//        if (!isLoadingMoreItemsInProgress) {
//            isLoadingMoreItemsInProgress = true
//
//            val lastPostId = postsListAdapter.getLastPostId()
//            lastPostId?.let {
//                val lastPostId = it
//                val callback = this
//                viewModel.fetchMoreRedditPostsFromServer(callback, lastPostId)
//            }
//        }
//    }

    private fun setViewState(state: String) {
        when (state) {
            STATE_INITIAL_LOADING_IN_PROGRESS -> setupInitialLoadingInProgressView()
            STATE_INITIAL_LOADING_ERROR -> setupInitialLoadingErrorView()
            STATE_LIST_CONTENT_LOADED_SUCCESSFULLY -> setupListContentLoadedSuccessfullyView()
            STATE_ERROR_OCCURS_WHEN_LIST_CONTENT_IS_LOADED -> setupErrorOccursWhenListContentIsLoadedView()
        }
    }

    private fun setupInitialLoadingInProgressView() {
        setContent {
            ProgressBar(isVisible = true)
        }
    }

    private fun setupInitialLoadingErrorView() {
//        // Stop the loading progress bar (circle)
//        progressBar.setContent { MdcTheme { ProgressBar(false) } }
//
//        // Display "Try Again" button
//        tryagain_button.setContent { MdcTheme { TryAgainButton(true) } }
//
//        // Display error message to the user
//        displayToastErrorMessageToTheUser()
    }

    private fun setupListContentLoadedSuccessfullyView() {
//        // Hide the loading view. We hide one big container with our "Loading view" here,
//        // and make the other big container (that will hold our list) visible.
//        loading_container.visibility = View.GONE
//        appbar_container.visibility = View.VISIBLE
//
//        // Setup refresh button
//        btn_refresh.setOnClickListener {
//            refreshPostsSubscription()
//        }

    }

    private fun setupErrorOccursWhenListContentIsLoadedView() {
        // Display error message to the user
        displayToastErrorMessageToTheUser()
    }

    private fun tryAgainButtonClicked() {
        // Reset the feed data subscription
        refreshPostsSubscription()

        // Re-display the loading progress bar (circle)
        progressBar.setContent { MdcTheme { ProgressBar(true) } }
    }

    private fun displayToastErrorMessageToTheUser() {
        Toast.makeText(
            this,
            R.string.network_problem_check_internet_connection,
            Toast.LENGTH_SHORT
        ).show()
    }

    // RedditPostsFetchingInterface Functions

    override fun redditPostsFetchedSuccessfully(list: List<RedditPostModel>) {
        if (list.isNotEmpty()) {
            setViewState(STATE_LIST_CONTENT_LOADED_SUCCESSFULLY)

            // Display fetched items
            setContent {
                PostsList(posts = list)
            }
        }

        isLoadingMoreItemsInProgress = false
    }

    override fun redditPostsRefreshedSuccessfully(list: List<RedditPostModel>) {
        if (list.isNotEmpty()) {
            setViewState(STATE_LIST_CONTENT_LOADED_SUCCESSFULLY)

            // Display fetched items
//            postsListAdapter.addFreshPosts(list)
        }

        isLoadingMoreItemsInProgress = false
    }

    override fun redditPostsFetchingError() {
//        if (isListContentAlreadyLoaded()) setViewState(
//            STATE_ERROR_OCCURS_WHEN_LIST_CONTENT_IS_LOADED
//        )
//        else setViewState(STATE_INITIAL_LOADING_ERROR)
//
//        isLoadingMoreItemsInProgress = false
    }

//    private fun isListContentAlreadyLoaded(): Boolean {
//        return postsListAdapter.itemCount > 0
//    }
}

@Composable
private fun ProgressBar(isVisible: Boolean) {
    if (isVisible) {
        CircularProgressIndicator(modifier = Modifier.padding(0.dp, 30.dp, 0.dp, 0.dp))
    }
}

@Composable
private fun PostsList(posts: List<RedditPostModel>) {
    // Use LazyRow when making horizontal lists
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(posts.size) { index ->
            PostsListItem(posts[index])
        }
    }
}

@Composable
private fun PostsListItem(post: RedditPostModel) {
    Column {
        post.let { it ->
            Image(
                painter = rememberAsyncImagePainter(it.thumbnail),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(200.dp)
            )
            post.title?.let {
                androidx.compose.material.Text(
                    text = it,
                    style = androidx.compose.material.MaterialTheme.typography.subtitle1
                )
            }
            post.author?.let {
                androidx.compose.material.Text(
                    text = it,
                    style = androidx.compose.material.MaterialTheme.typography.subtitle2
                )
            }
        }
    }
}