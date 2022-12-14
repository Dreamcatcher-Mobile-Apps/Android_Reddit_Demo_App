package redditandroidapp.features.feed

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.appbar.*
import kotlinx.android.synthetic.main.loading_badge.*
import redditandroidapp.R
import redditandroidapp.data.database.RedditPostModel

// Main ('feed') view
@AndroidEntryPoint
class FeedActivity : AppCompatActivity(), RedditPostsFetchingInterface {

    private val viewModel: FeedViewModel by viewModels()

    private lateinit var postsListAdapter: PostsListAdapter
    private var isLoadingMoreItemsInProgress: Boolean = false

    private val STATE_INITIAL_LOADING_IN_PROGRESS = "STATE_INITIAL_LOADING_IN_PROGRESS"
    private val STATE_INITIAL_LOADING_ERROR = "STATE_INITIAL_LOADING_ERROR"
    private val STATE_LIST_CONTENT_LOADED_SUCCESSFULLY = "STATE_LIST_CONTENT_LOADED_SUCCESSFULLY"
    private val STATE_ERROR_OCCURS_WHEN_LIST_CONTENT_IS_LOADED = "STATE_ERROR_OCCURS_WHEN_LIST_CONTENT_IS_LOADED"

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI
        setViewState(STATE_INITIAL_LOADING_IN_PROGRESS)

        // Initialize RecyclerView (feed items)
        setupRecyclerView()

        // Fetch feed items from the back-end and load them into the view
        fetchRedditPostsFromServer()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    // Jetpack Compose UI functions:

    @Composable
    private fun AppTitle() {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.h5
        )
    }

    @Composable
    private fun LoadingHeader() {
        Text(
            text = stringResource(R.string.loading),
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(0.dp, 30.dp, 0.dp, 0.dp)
        )
    }

    @Composable
    private fun ProgressBar(isVisible: Boolean) {
        if (isVisible) {
            CircularProgressIndicator(modifier = Modifier.padding(0.dp, 30.dp, 0.dp, 0.dp))
        }
    }

    @Composable
    private fun TryAgainButton(isVisible: Boolean) {
        if (isVisible) {
            OutlinedButton(
                onClick = { tryAgainButtonClicked() },
                modifier = Modifier.padding(0.dp, 30.dp, 0.dp, 0.dp)
            ) {
                Text(getString(R.string.try_again))
            }
        }
    }

    @Preview
    @Composable
    fun ComposablePreview() {
        AppTitle()
        LoadingHeader()
        CircularProgressIndicator()
        TryAgainButton(true)
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////


    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        main_feed_recyclerview.layoutManager = layoutManager
        postsListAdapter = PostsListAdapter()
        main_feed_recyclerview.adapter = postsListAdapter
        main_feed_recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (layoutManager.findLastVisibleItemPosition() + 1 == postsListAdapter.itemCount) {
                    loadMoreItems()
                }
            }
        })
    }

    private fun fetchRedditPostsFromServer() {
        viewModel.fetchRedditPostsFromServer(this)
    }

    private fun refreshPostsSubscription() {
        viewModel.fetchRefreshedRedditPostsFromServer(this)
    }

    private fun loadMoreItems() {
        if (!isLoadingMoreItemsInProgress) {
            isLoadingMoreItemsInProgress = true

            val lastPostId = postsListAdapter.getLastPostId()
            lastPostId?.let {
                val lastPostId = it
                val callback = this
                viewModel.fetchMoreRedditPostsFromServer(callback, lastPostId)
            }
        }
    }

    private fun setViewState(state: String) {
        when(state) {
            STATE_INITIAL_LOADING_IN_PROGRESS -> setupInitialLoadingInProgressView()
            STATE_INITIAL_LOADING_ERROR -> setupInitialLoadingErrorView()
            STATE_LIST_CONTENT_LOADED_SUCCESSFULLY -> setupListContentLoadedSuccessfullyView()
            STATE_ERROR_OCCURS_WHEN_LIST_CONTENT_IS_LOADED -> setupErrorOccursWhenListContentIsLoadedView()
        }
    }

    private fun setupInitialLoadingInProgressView() {
        app_title.setContent { MdcTheme { AppTitle() } }
        loading_header.setContent { MdcTheme { LoadingHeader() } }
        progressBar.setContent { MdcTheme { ProgressBar(true) } }
        tryagain_button.setContent { MdcTheme { TryAgainButton(false) } }
    }

    private fun setupInitialLoadingErrorView() {
        // Stop the loading progress bar (circle)
        progressBar.setContent { MdcTheme { ProgressBar(false) } }

        // Display "Try Again" button
        tryagain_button.setContent { MdcTheme { TryAgainButton(true) } }

        // Display error message to the user
        displayToastErrorMessageToTheUser()
    }

    private fun setupListContentLoadedSuccessfullyView() {
        // Hide the loading view. We hide one big container with our "Loading view" here,
        // and make the other big container (that will hold our list) visible.
        loading_container.visibility = View.GONE
        appbar_container.visibility = View.VISIBLE

        // Setup refresh button
        btn_refresh.setOnClickListener{
            refreshPostsSubscription()
        }
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
        Toast.makeText(this, R.string.network_problem_check_internet_connection, Toast.LENGTH_SHORT).show()
    }

    // RedditPostsFetchingInterface Functions

    override fun redditPostsFetchedSuccessfully(list: List<RedditPostModel>) {
        if (list.isNotEmpty()) {
            setViewState(STATE_LIST_CONTENT_LOADED_SUCCESSFULLY)

            // Display fetched items
            postsListAdapter.addMorePosts(list)
        }

        isLoadingMoreItemsInProgress = false
    }

    override fun redditPostsRefreshedSuccessfully(list: List<RedditPostModel>) {
        if (list.isNotEmpty()) {
            setViewState(STATE_LIST_CONTENT_LOADED_SUCCESSFULLY)

            // Display fetched items
            postsListAdapter.addFreshPosts(list)
        }

        isLoadingMoreItemsInProgress = false
    }

    override fun redditPostsFetchingError() {
        if (isListContentAlreadyLoaded()) setViewState(STATE_ERROR_OCCURS_WHEN_LIST_CONTENT_IS_LOADED)
        else setViewState(STATE_INITIAL_LOADING_ERROR)

        isLoadingMoreItemsInProgress = false
    }

    private fun isListContentAlreadyLoaded(): Boolean {
        return postsListAdapter.itemCount > 0
    }
}