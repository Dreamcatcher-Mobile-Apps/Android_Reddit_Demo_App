//package redditandroidapp.features.feed
//
//import android.os.Build
//import android.os.Bundle
//import android.view.View
//import android.widget.Toast
//import androidx.activity.compose.setContent
//import androidx.activity.viewModels
//import androidx.annotation.RequiresApi
//import androidx.appcompat.app.AppCompatActivity
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.material.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import coil.compose.rememberAsyncImagePainter
//import com.google.android.material.composethemeadapter.MdcTheme
//import dagger.hilt.android.AndroidEntryPoint
//import kotlinx.android.synthetic.main.activity_main.*
//import kotlinx.android.synthetic.main.appbar.*
//import kotlinx.android.synthetic.main.loading_badge.*
//import redditandroidapp.R
//import redditandroidapp.data.models.RedditPostModel
//
//// Main ('feed') view
//@AndroidEntryPoint
//class FeedActivity : AppCompatActivity(), RedditPostsFetchingInterface {
//
//    @RequiresApi(Build.VERSION_CODES.M)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }
//
//    ////////////////////////////////////////////////////////////////////////////////////////////////
//
//    // Jetpack Compose UI functions:
//
//    @Composable
//    private fun AppTitle() {
//        Text(
//            text = stringResource(R.string.app_name),
//            style = MaterialTheme.typography.h5
//        )
//    }
//
//    @Composable
//    private fun LoadingHeader() {
//        Text(
//            text = stringResource(R.string.loading),
//            style = MaterialTheme.typography.h6,
//            modifier = Modifier.padding(0.dp, 30.dp, 0.dp, 0.dp)
//        )
//    }
//
//    @Composable
//    private fun ProgressBar(isVisible: Boolean) {
//        if (isVisible) {
//            CircularProgressIndicator(modifier = Modifier.padding(0.dp, 30.dp, 0.dp, 0.dp))
//        }
//    }
//
//    @Composable
//    private fun TryAgainButton(isVisible: Boolean) {
//        if (isVisible) {
//            OutlinedButton(
//                onClick = { tryAgainButtonClicked() },
//                modifier = Modifier.padding(0.dp, 30.dp, 0.dp, 0.dp)
//            ) {
//                Text(getString(R.string.try_again))
//            }
//        }
//    }
//
//    @Composable
//    private fun PostsList(posts: List<RedditPostModel>) {
//        // Use LazyRow when making horizontal lists
//        LazyColumn(modifier = Modifier.fillMaxSize()) {
//            items(posts.size) { index ->
//                PostsListItem(posts[index])
//            }
//        }
//    }
//
//    @Composable
//    private fun PostsListItem(post: RedditPostModel) {
//        Column {
//            post.link?.let { it ->
//                Image(
//                    painter = rememberAsyncImagePainter(it),
//                    contentDescription = null,
//                    modifier = Modifier.fillMaxWidth()
//                )
//                post.title?.let { Text(text = it, style = MaterialTheme.typography.subtitle1) }
//                post.author?.let { Text(text = it, style = MaterialTheme.typography.subtitle2) }
//            }
//        }
//    }
//
//    @Preview
//    @Composable
//    fun ComposablePreview() {
//        AppTitle()
//        LoadingHeader()
//        CircularProgressIndicator()
//        TryAgainButton(true)
//    }
//
//    ////////////////////////////////////////////////////////////////////////////////////////////////
//
//
////    private fun setupRecyclerView() {
////        val layoutManager = LinearLayoutManager(this)
////        main_feed_recyclerview.layoutManager = layoutManager
////        postsListAdapter = PostsListAdapter()
////        main_feed_recyclerview.adapter = postsListAdapter
////        main_feed_recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
////            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
////                super.onScrollStateChanged(recyclerView, newState)
////                if (layoutManager.findLastVisibleItemPosition() + 1 == postsListAdapter.itemCount) {
////                    loadMoreItems()
////                }
////            }
////        })
////    }
//
//
//}
//
