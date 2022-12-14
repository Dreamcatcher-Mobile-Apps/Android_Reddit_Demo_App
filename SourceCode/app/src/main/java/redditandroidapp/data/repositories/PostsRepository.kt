package redditandroidapp.data.repositories

import redditandroidapp.data.database.RedditPostModel
import redditandroidapp.data.network.ApiClient
import redditandroidapp.data.network.PostsResponseGsonModel
import redditandroidapp.data.network.SinglePostDataGsonModel
import redditandroidapp.features.feed.RedditPostsFetchingInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

// Data Repository - the main gate of the model (data) part of the application
class PostsRepository @Inject constructor(private val apiClient: ApiClient) {

    fun getAllPosts(callback: RedditPostsFetchingInterface, lastPostName: String?, refreshPostsTriggered: Boolean) {
        val endpoint = if (lastPostName == null) apiClient.getFreshRedditPosts()
        else apiClient.getNextPageOfRedditPosts(lastPostName)

        endpoint.enqueue(object: Callback<PostsResponseGsonModel> {

            override fun onResponse(call: Call<PostsResponseGsonModel>?, response: Response<PostsResponseGsonModel>?) {
                response?.body()?.data?.childrenPosts?.let {
                    val receivedList = it
                    val transformedList = transformReceivedRedditPostsList(receivedList)
                    if (refreshPostsTriggered) callback.redditPostsRefreshedSuccessfully(transformedList)
                    else callback.redditPostsFetchedSuccessfully(transformedList)
                }
            }

            override fun onFailure(call: Call<PostsResponseGsonModel>?, t: Throwable?) {
                callback.redditPostsFetchingError()
            }
        })
    }

    private fun transformReceivedRedditPostsList(list: List<SinglePostDataGsonModel>): List<RedditPostModel> {
        return list.map {
            RedditPostModel (
                it.post?.permalink,
                it.post?.title,
                it.post?.thumbnail,
                it.post?.author,
                it.post?.name
            )
        }
    }
}