package redditandroidapp.data.models

data class RedditPostModel (
        val id: String,
        val link: String?,
        val title: String?,
        val thumbnail: String?,
        val author: String?,
        val text: String?
)