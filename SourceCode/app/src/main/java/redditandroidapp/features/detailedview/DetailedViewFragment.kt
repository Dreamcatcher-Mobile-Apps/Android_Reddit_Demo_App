package redditandroidapp.features.detailedview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.detailed_view.*
import redditandroidapp.R
import redditandroidapp.data.network.NetworkConstants

// Detailed view for displaying chosen item
@AndroidEntryPoint
class DetailedViewFragment : Fragment() {

    private val viewModel: DetailedViewViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.detailed_view, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Fetch detailed data from Data Repository
        subscribeForPost()

        // Setup Cross Button
        val closingOnClickListener = View.OnClickListener{ activity?.onBackPressed() }
        btn_cross.setOnClickListener(closingOnClickListener)

        // Setup closing on the grey fields' click
        spacing_top.setOnClickListener(closingOnClickListener)
        spacing_bottom.setOnClickListener(closingOnClickListener)
    }

    private fun subscribeForPost() {
        val postId = this.arguments?.getInt("postId")
        postId?.let {
            viewModel.getSingleSavedPostById(it)?.observe(viewLifecycleOwner) {
                val baseUrl = NetworkConstants.BASE_URL
                val specificUrl = it?.permalink
                specificUrl?.let {
                    val postUrl = baseUrl + specificUrl
                    setupWebView(postUrl)
                }
            }
        }
    }

    // Setup website view
    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView(url: String) {
        website_view.settings.javaScriptEnabled = true
        website_view.webViewClient = WebViewClient()
        website_view.loadUrl(url)
        showLoadingView(false)
    }

    private fun showLoadingView(loadingState: Boolean) {
        if (loadingState) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}