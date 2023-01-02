package redditandroidapp.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import redditandroidapp.data.models.RedditPostModel

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun Home(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val viewState by viewModel.state.collectAsStateWithLifecycle()
    Surface(Modifier.fillMaxSize()) {
        HomeContent(posts = viewState.redditPosts ?: emptyList())
    }
}

@Composable
fun HomeContent(
    posts: List<RedditPostModel>
) {
    PostsList(posts = posts)
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
                    style = MaterialTheme.typography.subtitle1
                )
            }
            post.author?.let {
                androidx.compose.material.Text(
                    text = it,
                    style = MaterialTheme.typography.subtitle2
                )
            }
        }
    }
}

//TODO: Fix preview (needs adding mock data to pass to this preview)
//@Composable
//@Preview
//fun PreviewHomeContent() {
//    JetcasterTheme {
//        HomeContent(
//            featuredPodcasts = PreviewPodcastsWithExtraInfo,
//            isRefreshing = false,
//            homeCategories = HomeCategory.values().asList(),
//            selectedHomeCategory = HomeCategory.Discover,
//            onCategorySelected = {},
//            onPodcastUnfollowed = {}
//        )
//    }
//}
