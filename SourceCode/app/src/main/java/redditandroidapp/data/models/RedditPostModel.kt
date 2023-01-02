package redditandroidapp.data.models

// Todo: Does it make sense to receive object that potentially have all nulls (which should not happen).
data class RedditPostModel (
        val link: String?,
        val title: String?,
        val thumbnail: String?,
        val author: String?,
        val name: String?
)
