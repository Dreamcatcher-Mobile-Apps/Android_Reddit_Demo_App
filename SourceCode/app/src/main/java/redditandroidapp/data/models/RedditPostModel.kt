package redditandroidapp.data.models

data class RedditPostModel (
        val link: String?,
        val title: String?,
        val thumbnail: String?,
        val author: String?,
        // Todo: Confirm it's ID and rename if so.
        val name: String
)