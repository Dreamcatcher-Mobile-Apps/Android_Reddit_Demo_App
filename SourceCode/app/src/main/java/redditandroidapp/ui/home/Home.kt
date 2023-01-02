package redditandroidapp.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
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
        HomeContent(posts = viewState.redditPosts ?: emptyList(),
            )
    }
}

@Composable
fun HomeContent(
    posts: List<RedditPostModel>
) {
    PostsList(posts = posts)
}

fun LazyListState.isScrolledToEnd() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1


@Composable
private fun PostsList(posts: List<RedditPostModel>) {
    val listState = rememberLazyListState()

    // Use LazyRow when making horizontal lists
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(posts.size) { index ->
            PostsListItem(posts[index])
        }
    }

    val endOfListReached by remember {
        derivedStateOf {
            listState.isScrolledToEnd()
        }
    }

    LaunchedEffect(endOfListReached) {

    }
}

@Composable
private fun PostsListItem(post: RedditPostModel) {
    Column {
        post.let { it ->
            Image(
                painter = rememberAsyncImagePainter(it.thumbnail),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(Modifier.height(16.dp))
            post.title?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.subtitle1
                )
            }
            Spacer(Modifier.height(16.dp))
            post.author?.let {
                Text(
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
