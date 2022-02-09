package redditandroidapp.data.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// External gate for communication with API endpoints (operated by Retrofit)
interface ApiClient {

    @GET("/r/Android/hot.json")
    fun getFreshPosts(): Call<PostsResponseGsonModel>

    @GET("/r/Android/hot.json")
    fun getNextPageOfPosts(@Query("after") lastPostName: String): Call<PostsResponseGsonModel>
}