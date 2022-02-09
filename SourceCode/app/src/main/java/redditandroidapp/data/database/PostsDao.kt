package redditandroidapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PostsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewPost(postDatabaseEntity: PostDatabaseEntity)

    @Query("SELECT * FROM posts WHERE id = :id LIMIT 1")
    fun getSingleSavedPostById(id: Int): LiveData<PostDatabaseEntity>?

    @Query("SELECT * FROM posts")
    fun getAllSavedPosts(): LiveData<List<PostDatabaseEntity>>?

    @Query("DELETE FROM posts")
    fun clearDatabase()
}