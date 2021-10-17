package redditandroidapp.features.feed

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.main_feed_list_item.view.*
import redditandroidapp.R
import redditandroidapp.data.database.PostDatabaseEntity

// Main adapter used for managing items list within the main RecyclerView (main feed listed)
class PostsListAdapter (val context: Context, val clickListener: (Int) -> Unit) : RecyclerView.Adapter<ViewHolder>() {

    private var postsList: List<PostDatabaseEntity> = ArrayList()

    fun setPosts(newPostsList: List<PostDatabaseEntity>) {
        if (newPostsList.size > this.postsList.size && this.postsList.isNotEmpty()) {
            addMorePosts(newPostsList)
        } else {
            addFreshPosts(newPostsList)
        }
    }

    fun addFreshPosts(newPostsList: List<PostDatabaseEntity>) {
        this.postsList = newPostsList
        notifyDataSetChanged()
    }

    fun addMorePosts(newPostsList: List<PostDatabaseEntity>) {
        val updateStartPoint = postsList.size
        val updateEndPoint = postsList.size + newPostsList.size - 1
        this.postsList = newPostsList
        notifyItemRangeInserted(updateStartPoint, updateEndPoint)
    }

    override fun getItemCount(): Int {
        return postsList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.main_feed_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // Prepare fetched data
        val title = postsList[position].title
        val author = postsList[position].author
        val urlToImage = postsList[position].thumbnail

        // Set the picture
        Glide.with(context)
            .load(urlToImage)
            .into(holder.picture)

        // Set data within the holder
        holder.title.text = title
        holder.author.text = author

        // Set onClickListener
        holder.rowContainer.setOnClickListener{
            val itemId = postsList[position].id
            clickListener(itemId)
        }
    }

    fun getLastPostId(): String? {
        return postsList.last().name
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val picture = view.imageView_picture
    val title = view.textView_title
    val author = view.textView_author
    val rowContainer = view.row_container
}