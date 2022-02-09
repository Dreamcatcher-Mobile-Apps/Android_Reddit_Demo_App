package redditandroidapp.data.database

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import redditandroidapp.data.network.SinglePostDataGsonModel

// Interactor used for communication with the internal database
class PostsDatabaseInteractor(private val postsDatabase: PostsDatabase) {

    fun getSingleSavedPostById(id: Int): LiveData<PostDatabaseEntity>? {
        return postsDatabase.getPostsDao().getSingleSavedPostById(id)
    }

    fun getAllPosts(): LiveData<List<PostDatabaseEntity>>? {
        return postsDatabase.getPostsDao().getAllSavedPosts()
    }

    fun updatePosts(posts: List<SinglePostDataGsonModel>) {
        GlobalScope.launch(Dispatchers.IO) {

            // Clear database not to store outdated items
            postsDatabase.getPostsDao().clearDatabase()

            // Save freshly fetched items
            posts.forEach {
                val postEntity = PostDatabaseEntity(
                    permalink = it.post?.permalink,
                    title = it.post?.title,
                    thumbnail = it.post?.thumbnail,
                    author = it.post?.author,
                    name = it.post?.name
                )

                postsDatabase.getPostsDao().insertNewPost(postEntity)
            }
        }
    }

    fun addNextPageOfPosts(posts: List<SinglePostDataGsonModel>) {
        GlobalScope.launch(Dispatchers.IO) {

            // Save freshly fetched items
            posts.forEach {
                val postEntity = PostDatabaseEntity(
                    permalink = it.post?.permalink,
                    title = it.post?.title,
                    thumbnail = it.post?.thumbnail,
                    author = it.post?.author,
                    name = it.post?.name
                )

                postsDatabase.getPostsDao().insertNewPost(postEntity)
            }
        }
    }
}



