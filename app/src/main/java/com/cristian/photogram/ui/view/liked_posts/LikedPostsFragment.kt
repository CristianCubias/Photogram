package com.cristian.photogram.ui.view.liked_posts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.cristian.photogram.R
import com.cristian.photogram.databinding.FragmentLikedPostsBinding
import com.cristian.photogram.domain.model.Post
import com.cristian.photogram.domain.model.Resource
import com.cristian.photogram.ui.viewmodel.MainViewModel
import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog
import dev.shreyaspatil.MaterialDialog.model.TextAlignment
import kotlinx.coroutines.launch

class LikedPostsFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private var _binding : FragmentLikedPostsBinding? = null
    private val binding get() = _binding!!

    private var _adapter: LikedPostsAdapter? = null
    private val adapter: LikedPostsAdapter get() = _adapter!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = FragmentLikedPostsBinding.inflate(layoutInflater)
        _adapter = LikedPostsAdapter(this::onPostClicked)
        binding.likedPostsRv.layoutManager = LinearLayoutManager(context)
        binding.likedPostsRv.adapter = adapter
        setSwipeRefresh()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.getLikedPosts().collect {
                    adapter.submitData(viewLifecycleOwner.lifecycle, it)
                }
            }
        }
        adapter.addLoadStateListener { state ->
            if (state.refresh is LoadState.Error) {
                Log.e("LikedPostsFragment", "Error: ${(state.refresh as LoadState.Error).error}")
            }
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        _adapter = null
    }

    /**
     * This function is called when a post is clicked.
     * Depending of the button clicked, it will show the post details or
     * remove the post from the liked posts list.
     */
    private fun onPostClicked(post: Post, btnID: String) {
        /**
         *   If he user clicks on the "like" button,
         *   we want to delete the post from the database
         *   and refresh the list.
         */
        if(btnID == "like"){
            when(val result = viewModel.removeLikedPost(post.id)){
                is Resource.Success -> {
                    adapter.refresh()
                    Toast.makeText(context, "Post removed from liked posts", Toast.LENGTH_SHORT).show()
                }
                is Resource.Error -> {
                    Log.e("LikedPostsFragment", "Error removing post from liked posts: ${result.error}")
                    Toast.makeText(context, "Error removing posts from liked posts", Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
        else if (btnID == "details"){
            BottomSheetMaterialDialog.Builder(requireActivity())
                .setTitle(getString(R.string.details_label))
                .setMessage(
                    HtmlCompat.fromHtml(
                    "<b>${getString(R.string.width_label)}</b>: ${post.width}px<br>" +
                            "<b>${getString(R.string.height_label)}</b>: ${post.height}px <br>" +
                            "<b>${getString(R.string.views_label)}</b>: ${post.views} <br>" +
                            "<b>${getString(R.string.download_count_label)}</b>: ${post.downloads}"
                    , HtmlCompat.FROM_HTML_MODE_LEGACY), TextAlignment.START)
                .setCancelable(true)
                .build()
                .show()
        }
    }

    private fun setSwipeRefresh(){
        binding.swipeRefresh.setOnRefreshListener {
            adapter.refresh()
            binding.swipeRefresh.isRefreshing = false
        }
    }
}