package redditandroidapp.features.detailedview

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import redditandroidapp.data.database.PostDatabaseEntity
import redditandroidapp.data.repositories.PostsRepository
import javax.inject.Inject

@HiltViewModel
class DetailedViewViewModel @Inject constructor(private val postsRepository: PostsRepository)
    : ViewModel(), LifecycleObserver {

    fun getSingleSavedPostById(id: Int): LiveData<PostDatabaseEntity>? {
        return postsRepository.getSingleSavedPostById(id)
    }
}