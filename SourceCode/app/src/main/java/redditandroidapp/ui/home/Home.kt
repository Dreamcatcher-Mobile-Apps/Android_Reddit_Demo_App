package redditandroidapp.ui.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import redditandroidapp.R
import redditandroidapp.data.models.RedditPostModel
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun Home(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val viewState = viewModel.stateData
    val listState = rememberLazyListState()

    Surface(Modifier.fillMaxSize()) {
        HomeContent(
            state = viewState,
            listState = listState,
            onEndOfListReached = viewModel::fetchMorePostsRequested,
            onRefreshPressed = viewModel::refreshPostsRequested
        )
    }
}

@Composable
private fun HomeContent(
    state: StateData,
    listState: LazyListState,
    onEndOfListReached: () -> Unit,
    onRefreshPressed: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppBar(onRefreshPressed)
        state.errorMessage?.let {
            Toast.makeText(LocalContext.current, it, Toast.LENGTH_SHORT).show()
        }
        // Todo: Error dialog?
        if (state.errorMessage == null && !state.isLoading) {
            Toast.makeText(LocalContext.current, stringResource(R.string.toast_message), Toast.LENGTH_SHORT).show()
        }

        BoxWithConstraints(contentAlignment = Alignment.Center) {
            PostsList(
                posts = state.posts,
                listState = listState,
                onEndOfListReached = onEndOfListReached,
            )
            if (state.isLoading) {
                LoadingSpinner(
                    modifier = Modifier
                        .padding(vertical = 24.dp)
                        .size(50.dp)
                )
            }
        }
        Spacer(Modifier.height(8.dp))
    }
}

@Composable
private fun AppBar(onRefreshPressed: () -> Unit) {
    TopAppBar(elevation = 4.dp,
        title = { Text(stringResource(R.string.app_name)) },
        backgroundColor = MaterialTheme.colors.primarySurface,
        actions = {
            IconButton(onClick = onRefreshPressed) {
                Icon(Icons.Filled.Refresh, null)
            }
        })
}

@Composable
private fun errorDialog(onRefreshPressed: () -> Unit) {
    AlertDialog(
        onDismissRequest = {},
        title = {
            Text(text = "Oops!")
        },
        text = {
            Text("There was an error fetching Reddit posts. Tap here to try again.")
        },
        confirmButton = {
            Button(onClick = {
                onRefreshPressed()
            }) {
                Text("Retry")
            }
        },
    )
    Toast.makeText(
        LocalContext.current, R.string.connection_error_message, Toast.LENGTH_SHORT
    ).show()
}

@Composable
private fun PostsList(
    posts: List<RedditPostModel>,
    listState: LazyListState,
    onEndOfListReached: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = listState,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(posts.size) { index ->
            PostsListItem(posts[index])
        }
        item {
            LaunchedEffect(true) {
                onEndOfListReached.invoke()
            }
//            LoadingSpinner(modifier = Modifier
//                .padding(vertical = 12.dp)
//                .size(40.dp))
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
                        text = it, style = MaterialTheme.typography.subtitle1
                    )
                }
                Spacer(Modifier.height(16.dp))
                post.text?.let {
                    Text(text = it, maxLines = 2, overflow = TextOverflow.Ellipsis,  style = MaterialTheme.typography.subtitle1)
                }
                Spacer(Modifier.height(16.dp))
                post.author?.let {
                    Text(
                        text = it, style = MaterialTheme.typography.subtitle2
                    )
                }
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun LoadingSpinner(modifier: Modifier = Modifier) {
    CircularProgressIndicator(strokeWidth = 3.dp, modifier = modifier)
}

// Todo: Implement Jetpack Compose preview (needs adding mock data to be passed to this preview).
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
