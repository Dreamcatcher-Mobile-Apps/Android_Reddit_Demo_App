package redditandroidapp.data.repositories

import android.util.Log
import redditandroidapp.R
import redditandroidapp.data.models.RedditPostModel
import redditandroidapp.data.network.ApiClient
import redditandroidapp.data.network.PostsResponseGsonModel
import redditandroidapp.data.network.SinglePostDataGsonModel
import redditandroidapp.injection.RedditAndroidApp
import redditandroidapp.ui.home.PostsFetchingCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

// Data Repository - the main gate of the model (data) part of the application
class PostsRepository @Inject constructor(private val apiClient: ApiClient) {

    private val _redditPosts = ArrayList<RedditPostModel>()

    val redditPosts: List<RedditPostModel>
        get() = _redditPosts

    fun getLastPostName(): String? {
        return if (redditPosts.isNotEmpty()) redditPosts.last().id else null
    }

    fun fetchRedditPosts(
        lastPostName: String?,
        callback: PostsFetchingCallback,
        clearCache: Boolean
    ) {
        val endpoint = if (lastPostName == null) apiClient.getFreshRedditPosts()
        else apiClient.getNextPageOfRedditPosts(lastPostName)

        endpoint.enqueue(object : Callback<PostsResponseGsonModel> {

            override fun onResponse(
                call: Call<PostsResponseGsonModel>,
                response: Response<PostsResponseGsonModel>
            ) {
                if (response.isSuccessful
                    && response.body() != null
                    && response.body()?.data != null
                    && !response.body()?.data?.childrenPosts.isNullOrEmpty()
                )
                    response.body()?.data?.childrenPosts?.let {
                        val receivedList = it
                        val transformedList = transformReceivedRedditPostsList(receivedList)

                        if (clearCache) _redditPosts.clear()
                        _redditPosts.addAll(transformedList)

                        callback.postsFetchedSuccessfully(redditPosts)
                    }
                else {
                    val transformedErrorMessage = logErrorDetails(response.errorBody()?.string())
                    callback.postsFetchingError(transformedErrorMessage)
                }
            }

            override fun onFailure(call: Call<PostsResponseGsonModel>, t: Throwable) {
                logErrorDetails(t.message)
                callback.postsFetchingError(getUserFacingErrorMessage(t))
            }
        })
    }

    fun fetchCachedPosts(): List<RedditPostModel> {
        return redditPosts
    }

    private fun getUserFacingErrorMessage(fetchingError: Throwable?): String {
        val genericErrorMessage =
            RedditAndroidApp.getLocalResources().getString(R.string.connection_error_message)
        return fetchingError?.message ?: genericErrorMessage
    }

    private fun logErrorDetails(errorTextFromApi: String?): String {
        val localResources = RedditAndroidApp.getLocalResources()
        val errorTag = localResources.getString(R.string.error)
        val errorApiFetchingGenericText = localResources.getString(R.string.error_api_call_failure)
        val finalErrorText = errorTextFromApi ?: errorApiFetchingGenericText
        Log.e(errorTag, finalErrorText)
        return finalErrorText
    }

    private fun transformReceivedRedditPostsList(list: List<SinglePostDataGsonModel>): List<RedditPostModel> {
        return list.mapNotNull {
            it.post?.let {
                RedditPostModel(
                    it.id,
                    it.permalink,
                    it.title,
                    it.thumbnail,
                    it.author,
                )
            }
        }
    }
}