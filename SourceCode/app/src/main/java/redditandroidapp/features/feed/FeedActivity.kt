package redditandroidapp.features.feed

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.appbar.*
import kotlinx.android.synthetic.main.loading_badge.*
import redditandroidapp.R
import redditandroidapp.data.database.PostDatabaseEntity
import redditandroidapp.features.detailedview.DetailedViewFragment
import redditandroidapp.injection.RedditAndroidApp
import javax.inject.Inject


// Main ('feed') view
class FeedActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: FeedViewModel
    private lateinit var postsListAdapter: PostsListAdapter
    var isLoadingMoreItems: Boolean = false

    private val STATE_LOADING_ERROR = "STATE_LOADING_ERROR"
    private val STATE_CONTENT_LOADED = "STATE_CONTENT_LOADED"

    init {
        RedditAndroidApp.mainComponent.inject(this)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize ViewModel
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FeedViewModel::class.java)

        // Initialize RecyclerView (feed items)
        setupRecyclerView()

        // Fetch feed items from the back-end and load them into the view
        subscribeForFeedItems()

        // Catch and handle potential update (e.g. network) issues
        subscribeForUpdateError()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        main_feed_recyclerview.layoutManager = layoutManager
        postsListAdapter = PostsListAdapter(this) { postId: Int ->
            displayDetailedView(postId)
        }
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

    private fun loadMoreItems() {
        if (!isLoadingMoreItems) {
            isLoadingMoreItems = true
            val lastPostId = postsListAdapter.getLastPostId()
            lastPostId?.let {
                viewModel.fetchMorePosts(it)
            }
        }
    }

    private fun subscribeForFeedItems() {
        viewModel.subscribeForPosts(true)?.observe(this, Observer<List<PostDatabaseEntity>> {

            if (!it.isNullOrEmpty()) {
                setViewState(STATE_CONTENT_LOADED)

                // Display fetched items
                postsListAdapter.setPosts(it)
            }

            isLoadingMoreItems = false
        })
    }

    private fun subscribeForUpdateError() {
        viewModel.subscribeForUpdateErrors()?.observe(this, Observer<Boolean> {

            // Case of Network Error if no items have been cached
            if (postsListAdapter.itemCount == 0) {
                setViewState(STATE_LOADING_ERROR)
            }

            // Display error message to the user
            Toast.makeText(this, R.string.network_problem_check_internet_connection, Toast.LENGTH_LONG).show()

            isLoadingMoreItems = false
        })
    }

    private fun refreshPostsSubscription() {
        viewModel.refreshPosts()
    }

    private fun displayDetailedView(postId: Int) {
        val fragment = DetailedViewFragment()
        val bundle = Bundle()
        bundle.putInt("postId", postId)
        fragment.arguments = bundle

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.main_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setViewState(state: String) {
        when(state) {
            STATE_LOADING_ERROR -> setupLoadingErrorView()
            STATE_CONTENT_LOADED -> setupContentLoadedView()
        }
    }

    private fun setupLoadingErrorView() {
        // Stop the loading progress bar (circle)
        progressBar.visibility = View.INVISIBLE

        // Display "Try Again" button
        tryagain_button.visibility = View.VISIBLE

        // Setup onClick listener that resets the feed data subscription
        tryagain_button.setOnClickListener {
            refreshPostsSubscription()

            // Re-display the loading progress bar (circle)
            progressBar.visibility = View.VISIBLE
        }
    }

    private fun setupContentLoadedView() {
        // Hide the loading view
        loading_container.visibility = View.GONE
        appbar_container.visibility = View.VISIBLE

        // Setup refresh button
        btn_refresh.setOnClickListener{
            refreshPostsSubscription()
        }
    }
}
