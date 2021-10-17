package redditandroidapp.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import redditandroidapp.data.database.PostsDatabaseInteractor
import redditandroidapp.data.database.PostDatabaseEntity
import redditandroidapp.data.network.PostsNetworkInteractor
import redditandroidapp.data.repositories.PostsRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PostsRepositoryTest {

    private var postsRepository: PostsRepository? = null
    private var fakePostDatabaseEntity: PostDatabaseEntity? = null
    private var fakePostEntitiesList = ArrayList<PostDatabaseEntity>()

    @Mock
    private val postsDatabaseInteractor: PostsDatabaseInteractor? = null

    @Mock
    private val postsNetworkInteractor: PostsNetworkInteractor? = null

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {

        // Inject Mocks
        MockitoAnnotations.initMocks(this)

        // Initialize the repository
        postsRepository = PostsRepository(postsNetworkInteractor!!, postsDatabaseInteractor!!)

        // Prepare fake data
        val id = 0
        val title = "fake/post/title"
        val url = "fake/post/url"
        val imageUrl = "fake/post/image/url"
        val author = "fake/post/author"

        // Prepare fake Post Entity (DB object)
        fakePostDatabaseEntity = PostDatabaseEntity(id, url, title, imageUrl, author, null)

        // Prepare fake Posts Entities List
        fakePostEntitiesList.add(fakePostDatabaseEntity!!)
    }

    @Test
    fun fetchAllPostsByPostsRepository() {

        // Prepare LiveData structure
        val postsEntityLiveData = MutableLiveData<List<PostDatabaseEntity>>()
        postsEntityLiveData.setValue(fakePostEntitiesList);

        // Set testing conditions
        Mockito.`when`(postsDatabaseInteractor?.getAllPosts()).thenReturn(postsEntityLiveData)

        // Perform the action
        val storedPosts = postsRepository?.getAllPosts(false)

        // Check results
        Assert.assertSame(postsEntityLiveData, storedPosts);
    }

    @Test
    fun fetchSinglePostByPostsRepository() {

        // Prepare LiveData structure
        val postsEntityLiveData = MutableLiveData<PostDatabaseEntity>()
        postsEntityLiveData.setValue(fakePostDatabaseEntity);

        // Prepare fake post id
        val fakePostId = 0

        // Set testing conditions
        Mockito.`when`(postsDatabaseInteractor?.getSingleSavedPostById(fakePostId))
            .thenReturn(postsEntityLiveData)

        // Perform the action
        val storedPost = postsRepository?.getSingleSavedPostById(fakePostId)

        // Check results
        Assert.assertSame(postsEntityLiveData, storedPost);
    }
}