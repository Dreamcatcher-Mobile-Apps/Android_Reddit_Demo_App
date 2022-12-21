package redditandroidapp.features.feed

import redditandroidapp.data.models.RedditPostModel

interface RedditPostsFetchingInterface {

    fun redditPostsFetchedSuccessfully(list: List<RedditPostModel>)

    fun redditPostsRefreshedSuccessfully(list: List<RedditPostModel>)

    fun redditPostsFetchingError()

}