package redditandroidapp.data.repositories

import android.content.res.Resources
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import redditandroidapp.R
import redditandroidapp.data.models.RedditPostModel
import redditandroidapp.data.network.ApiClient
import redditandroidapp.data.network.PostsResponseGsonModel
import redditandroidapp.data.network.SinglePostDataGsonModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

// Data Repository - the main gate of the model (data) part of the application
class PostsRepository @Inject constructor(private val apiClient: ApiClient) {

    private val _redditPosts = MutableStateFlow<List<RedditPostModel>>(emptyList())

    val redditPosts: StateFlow<List<RedditPostModel>?>
        get() = _redditPosts

    fun fetchRedditPosts(lastPostName: String?) {
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

                    // Todo: Improve (global scope?).
                    GlobalScope.launch {
                        _redditPosts.getAndUpdate {
                            it.plus(transformedList)
                        }
                    }
                }
                else {
                    val transformedErrorMessage = logErrorDetails(response.errorBody()?.string())
                    throw Throwable(transformedErrorMessage)
                }
            }

            override fun onFailure(call: Call<PostsResponseGsonModel>, t: Throwable) {
                logErrorDetails(t.message)
                throw t
            }
        })
    }

    private fun logErrorDetails(errorTextFromApi: String?): String {
        val errorTag = Resources.getSystem().getString(R.string.error)
        val errorApiFetchingGenericText = Resources.getSystem().getString(R.string.error_api_call_failure)
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