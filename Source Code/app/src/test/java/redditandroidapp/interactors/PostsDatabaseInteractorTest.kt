package redditandroidapp.interactors

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import redditandroidapp.data.database.PostsDao
import redditandroidapp.data.database.PostsDatabase
import redditandroidapp.data.database.PostDatabaseEntity
import redditandroidapp.data.database.PostsDatabaseInteractor
import redditandroidapp.data.network.PostGsonModel
import redditandroidapp.data.network.PostsResponseGsonModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import redditandroidapp.data.network.ChildrenPostsDataGsonModel
import redditandroidapp.data.network.SinglePostDataGsonModel

class PostsDatabaseInteractorTest {

    private var postsDatabaseInteractor: PostsDatabaseInteractor? = null
    private var fakePostGsonModel: PostGsonModel? = null
    private var fakePostsResponseGsonModel: PostsResponseGsonModel? = null
    private var fakePostDatabaseEntity: PostDatabaseEntity? = null

    @Mock
    private val postsDatabase: PostsDatabase? = null

    @Mock
    private val postsDao: PostsDao? = null

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {

        // Inject Mocks
        MockitoAnnotations.initMocks(this)

        // Initialize the Interactor
        postsDatabaseInteractor = PostsDatabaseInteractor(postsDatabase!!)

        // Prepare fake data
        val id = 0
        val title = "fake/post/title"
        val url = "fake/post/url"
        val imageUrl = "fake/post/image/url"
        val author = "fake/post/author"

        // Prepare fake Gson (API) model objects
        fakePostGsonModel = PostGsonModel(url, title, imageUrl, author, null)
        fakePostsResponseGsonModel = PostsResponseGsonModel(
            ChildrenPostsDataGsonModel(
                listOf(SinglePostDataGsonModel(fakePostGsonModel!!))
            )
        )

        // Prepare fake Post Entity (DB object)
        fakePostDatabaseEntity = PostDatabaseEntity(id, url, title, imageUrl, author, null)
    }

    @Test
    fun fetchSinglePostByDatabaseInteractor() {

        // Prepare LiveData structure
        val postEntityLiveData = MutableLiveData<PostDatabaseEntity>()
        postEntityLiveData.setValue(fakePostDatabaseEntity);

        // Set testing conditions
        Mockito.`when`(postsDatabase?.getPostsDao()).thenReturn(postsDao)
        Mockito.`when`(postsDao?.getSingleSavedPostById(anyInt())).thenReturn(postEntityLiveData)

        // Perform the action
        val storedPost = postsDatabaseInteractor?.getSingleSavedPostById(0)

        // Check results
        Assert.assertSame(postEntityLiveData, storedPost);
    }

    @Test
    fun fetchAllPostsByDatabaseInteractor() {

        // Prepare LiveData structure
        val postEntityLiveData = MutableLiveData<List<PostDatabaseEntity>>()
        postEntityLiveData.setValue(listOf(fakePostDatabaseEntity!!))

        // Set testing conditions
        Mockito.`when`(postsDatabase?.getPostsDao()).thenReturn(postsDao)
        Mockito.`when`(postsDao?.getAllSavedPosts()).thenReturn(postEntityLiveData)

        // Perform the action
        val storedPost = postsDatabaseInteractor?.getAllPosts()

        // Check results
        Assert.assertSame(postEntityLiveData, storedPost);
    }
}