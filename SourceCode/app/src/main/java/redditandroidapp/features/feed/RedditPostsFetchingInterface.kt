package redditandroidapp.features.feed

import redditandroidapp.data.database.RedditPostModel

interface RedditPostsFetchingInterface {

    fun redditPostsFetchedSuccessfully(list: List<RedditPostModel>)

    fun redditPostsFetchingError()

}