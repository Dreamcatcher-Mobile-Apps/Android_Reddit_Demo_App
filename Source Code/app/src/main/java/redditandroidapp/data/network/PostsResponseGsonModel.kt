package redditandroidapp.data.network

import com.google.gson.annotations.SerializedName

// ApiResponse object used for deserializing data coming from API endpoint
data class PostsResponseGsonModel(
    @SerializedName("data")
    val data: ChildrenPostsDataGsonModel?
)

data class ChildrenPostsDataGsonModel(
    @SerializedName("children")
    val childrenPosts: List<SinglePostDataGsonModel>?
)

data class SinglePostDataGsonModel(
    @SerializedName("data")
    val post: PostGsonModel?
)