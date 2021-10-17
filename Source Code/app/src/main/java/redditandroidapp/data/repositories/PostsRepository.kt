package redditandroidapp.data.repositories

import androidx.lifecycle.LiveData
import redditandroidapp.data.database.PostsDatabaseInteractor
import redditandroidapp.data.database.PostDatabaseEntity
import redditandroidapp.data.network.PostsNetworkInteractor
import redditandroidapp.data.network.PostsResponseGsonModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

// Data Repository - the main gate of the model (data) part of the application
class PostsRepository @Inject constructor(private val networkInteractor: PostsNetworkInteractor,
                                          private val databaseInteractor: PostsDatabaseInteractor
) {

    fun getSingleSavedPostById(id: Int): LiveData<PostDatabaseEntity>? {
        return databaseInteractor.getSingleSavedPostById(id)
    }

    fun getAllPosts(backendUpdateRequired: Boolean): LiveData<List<PostDatabaseEntity>>? {
        if (backendUpdateRequired) {
            updateDataFromBackEnd()
        }
        return databaseInteractor.getAllPosts()
    }

    fun refreshPostsWithBackend() {
        updateDataFromBackEnd()
    }

    fun fetchMorePostsWithBackend(lastPostName: String) {
        fetchMorePosts(lastPostName)
    }

    fun subscribeForUpdateErrors(): LiveData<Boolean>? {
        return networkInteractor.getUpdateError()
    }

    fun setUpdateError(t: Throwable?) {
        networkInteractor.setUpdateError(t)
    }

    private fun updateDataFromBackEnd() {

        networkInteractor.getFreshPosts().enqueue(object: Callback<PostsResponseGsonModel> {

            override fun onResponse(call: Call<PostsResponseGsonModel>?, response: Response<PostsResponseGsonModel>?) {

                response?.body()?.data?.childrenPosts?.let {
                    databaseInteractor.updatePosts(it)
                }
            }

            override fun onFailure(call: Call<PostsResponseGsonModel>?, t: Throwable?) {
                setUpdateError(t)
            }
        })
    }

    private fun fetchMorePosts(lastPostName: String) {

        networkInteractor.getNextPageOfPosts(lastPostName).enqueue(object: Callback<PostsResponseGsonModel> {

            override fun onResponse(call: Call<PostsResponseGsonModel>?, response: Response<PostsResponseGsonModel>?) {

                response?.body()?.data?.childrenPosts?.let {
                    databaseInteractor.addNextPageOfPosts(it)
                }
            }

            override fun onFailure(call: Call<PostsResponseGsonModel>?, t: Throwable?) {
                setUpdateError(t)
            }
        })
    }
}