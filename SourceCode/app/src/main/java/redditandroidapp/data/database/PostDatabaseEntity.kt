package redditandroidapp.data.database

import androidx.room.PrimaryKey

data class PostDatabaseEntity(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        val permalink: String?,
        val title: String?,
        val thumbnail: String?,
        val author: String?,
        val name: String?
)

