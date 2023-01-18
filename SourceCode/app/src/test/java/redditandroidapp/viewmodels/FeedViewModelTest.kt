package redditandroidapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import redditandroidapp.data.models.RedditPostModel
import redditandroidapp.data.repositories.PostsRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class FeedViewModelTest {

    private var viewModel: FeedViewModel? = null
    private var fakeRedditPostModel: RedditPostModel? = null
    private var fakePostEntitiesList = ArrayList<RedditPostModel>()

    @Mock
    private val postsRepository: PostsRepository? = null

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {

        // Inject Mocks
        MockitoAnnotations.initMocks(this)

        // Initialize the ViewModel
        viewModel = FeedViewModel(postsRepository!!)

        // Prepare fake data
        val id = 0
        val title = "fake/post/title"
        val url = "fake/post/url"
        val imageUrl = "fake/post/image/url"
        val author = "fake/post/author"
        val text = "fake/post/author"

        // Prepare fake Database Entity
        fakeRedditPostModel = RedditPostModel(url, title, imageUrl, author, text, null)

        // Prepare fake Database Entities List
        fakePostEntitiesList.add(fakeRedditPostModel!!)
    }

    @Test
    fun fetchAllPostsByFeedViewModel() {

        // Prepare LiveData structure
        val postsEntityLiveData = MutableLiveData<List<RedditPostModel>>()
        postsEntityLiveData.value = fakePostEntitiesList

        // Set testing conditions
        //Mockito.`when`(postsRepository?.getRedditPosts(false)).thenReturn(postsEntityLiveData)

        // Perform the action
        //val storedPosts = viewModel?.fetchRedditPostsFromServer(false)

        // Check results
        //Assert.assertSame(postsEntityLiveData, storedPosts)
    }
}