package redditandroidapp.features.feed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import redditandroidapp.data.models.RedditPostModel
import redditandroidapp.features.feed.ui.theme.SourceCodeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            SourceCodeTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
//                    Greeting("Android")
//                }
//            }

            SourceCodeTheme {
                PostsList(posts = mockPosts())
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SourceCodeTheme {
        Greeting("Android")
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

private fun mockPosts(): List<RedditPostModel> {
    val post = RedditPostModel(
        title = "Pankracy gra na fujarce",
        thumbnail = "https://ocdn.eu/pulscms-transforms/1/zCmk9kpTURBXy80MzJjZTZkZDFiNjZiOTU2MWM2NTMyNDJlNDVkZjYyNC5qcGeTlQMABs0BLMyokwmmYmE4MDJiBpMFzQSwzQJ23gABoTAB/pankracy-fot-maciej-wasielewski-tvn24pl.jpg",
        author = "Pankracy",
        link = null,
        name = "Pankracy gra na gitarce"
    )
    return listOf(
        post, post, post
    )
}