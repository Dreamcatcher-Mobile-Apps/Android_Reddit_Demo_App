package redditandroidapp.data.network

import com.google.gson.annotations.SerializedName

// ApiResponse object used for deserializing data coming from API endpoint
data class PostGsonModel(
    @SerializedName("permalink")
    val permalink: String?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("thumbnail")
    val thumbnail: String?,

    @SerializedName("author")
    val author: String?,

    @SerializedName("name")
    val name: String?
)