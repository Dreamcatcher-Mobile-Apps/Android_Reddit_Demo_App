package redditandroidapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostDatabaseEntity(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        val permalink: String?,
        val title: String?,
        val thumbnail: String?,
        val author: String?,
        val name: String?
)

