package redditandroidapp.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import redditandroidapp.data.database.PostDatabaseEntity
import redditandroidapp.data.network.PostsNetworkInteractor
import redditandroidapp.data.network.PostsResponseGsonModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

// Data Repository - the main gate of the model (data) part of the application
class PostsRepository @Inject constructor(private val networkInteractor: PostsNetworkInteractor) {

    fun getAllPosts(): LiveData<List<PostDatabaseEntity>>? {
        val list = MutableLiveData<List<PostDatabaseEntity>>()
        networkInteractor.getFreshPosts().enqueue(object: Callback<PostsResponseGsonModel> {

            override fun onResponse(call: Call<PostsResponseGsonModel>?, response: Response<PostsResponseGsonModel>?) {

                response?.body()?.data?.childrenPosts?.let {
                    list.postValue(it.map {PostDatabaseEntity(0,
                        it.post?.permalink,
                        it.post?.title,
                        it.post?.thumbnail,
                        it.post?.author,
                        it.post?.name
                    ) })
                }
            }

            override fun onFailure(call: Call<PostsResponseGsonModel>?, t: Throwable?) {
                setUpdateError(t)
            }
        })
        return list
    }

    fun fetchMorePosts(lastPostName: String): LiveData<List<PostDatabaseEntity>>? {
        val list = MutableLiveData<List<PostDatabaseEntity>>()
        networkInteractor.getNextPageOfPosts(lastPostName).enqueue(object: Callback<PostsResponseGsonModel> {

            override fun onResponse(call: Call<PostsResponseGsonModel>?, response: Response<PostsResponseGsonModel>?) {

                response?.body()?.data?.childrenPosts?.let {
                    list.postValue(it.map {PostDatabaseEntity(0,
                        it.post?.permalink,
                        it.post?.title,
                        it.post?.thumbnail,
                        it.post?.author,
                        it.post?.name
                    ) })
                }
            }

            override fun onFailure(call: Call<PostsResponseGsonModel>?, t: Throwable?) {
                setUpdateError(t)
            }
        })
        return list
    }

    fun subscribeForUpdateErrors(): LiveData<Boolean> {
        return networkInteractor.getUpdateError()
    }

    fun setUpdateError(t: Throwable?) {
        networkInteractor.setUpdateError(t)
    }
}