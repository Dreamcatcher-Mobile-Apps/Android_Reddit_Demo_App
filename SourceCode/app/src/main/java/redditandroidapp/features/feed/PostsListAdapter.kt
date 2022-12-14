package redditandroidapp.features.feed

import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.recyclerview.widget.RecyclerView
import coil.compose.rememberAsyncImagePainter
import com.google.android.material.composethemeadapter.MdcTheme
import redditandroidapp.data.database.RedditPostModel

// Main adapter used for managing items list within the main RecyclerView (main feed listed)
class PostsListAdapter : RecyclerView.Adapter<ComposeViewHolder>() {

    private var postsList: List<RedditPostModel> = ArrayList()

    fun addFreshPosts(newPostsList: List<RedditPostModel>) {
        this.postsList = newPostsList
        notifyDataSetChanged()
    }

    fun addMorePosts(newPostsList: List<RedditPostModel>) {
        val updateStartPoint = postsList.size
        val updateEndPoint = postsList.size + newPostsList.size - 1
        this.postsList = this.postsList.plus(newPostsList)
        notifyItemRangeInserted(updateStartPoint, updateEndPoint)
    }

    override fun getItemCount(): Int {
        return postsList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComposeViewHolder {
        return ComposeViewHolder(ComposeView(parent.context))
    }

    override fun onBindViewHolder(holder: ComposeViewHolder, position: Int) {

        // Prepare data to display
        val title = postsList[position].title
        val author = postsList[position].author
        val urlToImage = postsList[position].thumbnail

        holder.bind(title, author, urlToImage)
    }

    // Jetpack Compose requirement
    override fun onViewRecycled(holder: ComposeViewHolder) {
        // Dispose the underlying Composition of the ComposeView
        // when RecyclerView has recycled this ViewHolder
        holder.view.disposeComposition()
    }

    fun getLastPostId(): String? {
        return postsList.last().name
    }
}

class ComposeViewHolder (val view: ComposeView) : RecyclerView.ViewHolder(view) {

    // Jetpack Compose requirement
    init {
        view.setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
    }

    fun bind(title: String?, author: String?, picture: String?) {
        view.setContent {
            Row {
                Column {
                    picture?.let { Image(
                        painter = rememberAsyncImagePainter(it),
                        contentDescription = null,
                        modifier = Modifier.size(128.dp)
                    ) }
                }
                Column {
                    MdcTheme {
                        // Todo: Unify Mdc and MaterialTheme?
                        title?.let { Text(text = it, style = MaterialTheme.typography.subtitle1) }
                        author?.let { Text(text = it, style = MaterialTheme.typography.subtitle2) }
                    }
                }
            }
        }
    }
}