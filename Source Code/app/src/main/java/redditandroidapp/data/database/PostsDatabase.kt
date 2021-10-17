package redditandroidapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(PostDatabaseEntity::class)], version = 1)
abstract class PostsDatabase : RoomDatabase() {
    abstract fun getPostsDao(): PostsDao
}