package redditandroidapp.data.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import redditandroidapp.data.network.NetworkConstants.Companion.REDDIT_ANDROID_HOT_POSTS_URL

// External gate for communication with API endpoints (operated by Retrofit)
interface ApiClient {

    @GET(REDDIT_ANDROID_HOT_POSTS_URL)
    fun getFreshRedditPosts(): Call<PostsResponseGsonModel>

    @GET(REDDIT_ANDROID_HOT_POSTS_URL)
    fun getNextPageOfRedditPosts(@Query("after") lastPostName: String): Call<PostsResponseGsonModel>
}