package redditandroidapp.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import redditandroidapp.R
import redditandroidapp.data.models.RedditPostModel

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun Home(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val viewState by viewModel.state.collectAsStateWithLifecycle()

    Surface(Modifier.fillMaxSize()) {
        HomeContent(
            posts = viewState.redditPosts ?: emptyList(),
            // TODO:  set correct callbacks for refreshing and adding new posts
            onEndOfListReached = viewModel::triggerMoreRedditPostsFetching,
            onRefreshPressed = viewModel::triggerFreshRedditPostsFetching
        )
    }
}

@Composable
private fun HomeContent(
    posts: List<RedditPostModel>,
    onEndOfListReached: () -> Unit,
    onRefreshPressed: () -> Unit
) {
    Column {
        AppBar(onRefreshPressed)
        Spacer(Modifier.height(8.dp))
        PostsList(posts = posts, onEndOfListReached = onEndOfListReached)
        Spacer(Modifier.height(8.dp))
    }
}

@Composable
private fun AppBar(onRefreshPressed: () -> Unit) {
    TopAppBar(
        elevation = 4.dp,
        title = {
            Text(stringResource(R.string.app_name))
        },
        backgroundColor = MaterialTheme.colors.primarySurface,
        actions = {
            IconButton(onClick = {
                onRefreshPressed.invoke()
            }) {
                Icon(Icons.Filled.Refresh, null)
            }
        })
}

@Composable
private fun PostsList(posts: List<RedditPostModel>, onEndOfListReached: () -> Unit) {
    val listState = rememberLazyListState()

    // Use LazyRow when making horizontal lists
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
    ) {
        items(posts.size) { index ->
            PostsListItem(posts[index])
        }
        item {
            LaunchedEffect(true) {
                onEndOfListReached.invoke()
            }
        }
    }
}

@Composable
private fun PostsListItem(post: RedditPostModel) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 8.dp)
            .fillMaxWidth()
    ) {
        post.let { it ->
            if (it.thumbnail?.endsWith(".jpg") == true || it.thumbnail?.endsWith(".png") == true) {
                Image(
                    painter = rememberAsyncImagePainter(it.thumbnail),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }
            Spacer(Modifier.height(16.dp))
            Column(Modifier.padding(horizontal = 16.dp)) {
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
                Spacer(Modifier.height(16.dp))
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
