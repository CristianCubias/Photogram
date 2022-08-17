package com.cristian.photogram.ui.view.feed

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
import androidx.recyclerview.widget.SimpleItemAnimator
import com.cristian.photogram.R
import com.cristian.photogram.databinding.FragmentFeedBinding
import com.cristian.photogram.domain.model.Post
import com.cristian.photogram.domain.model.Resource
import com.cristian.photogram.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog
import dev.shreyaspatil.MaterialDialog.model.TextAlignment
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FeedFragment : Fragment() {
    private val viewModel : MainViewModel by activityViewModels()
    private var _binding : FragmentFeedBinding? = null
    private val binding get() = _binding!!

    private var _adapter: PostAdapter? = null
    private val adapter: PostAdapter get() = _adapter!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = FragmentFeedBinding.inflate(layoutInflater)
        _adapter = PostAdapter(this::onPostClicked)
        binding.postRv.layoutManager = LinearLayoutManager(context)
        (binding.postRv.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        binding.postRv.adapter = adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.getPosts().collect {
                    adapter.submitData(viewLifecycleOwner.lifecycle, it)
                }
            }
        }
        adapter.addLoadStateListener { state ->
            when (val stateMsg = state.refresh) {
                is LoadState.Error -> {
                    Log.e("Feed Fragment", "Error: ${stateMsg.error}")
                } else -> {}
            }
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _adapter = null
        _binding = null
    }

    /**
     * This function is called when a post is clicked.
     * Depending on the button pressed, it will show the details of the post
     * or either remove or add the post to the database of liked posts.
     */
    private fun onPostClicked(post: Post, btnID: String, position: Int) {
        if(btnID == "like"){
            //Check if the post is already liked
            when(post.likedByUser){
                true -> {
                    //First, remove the post from the database, then update the UI
                    when(val result = viewModel.removeLikedPost(post.id)){
                        is Resource.Success -> {
                            adapter.snapshot().getOrNull(position)?.likedByUser = !post.likedByUser
                            adapter.notifyItemChanged(position)
                        }
                        is Resource.Error -> {
                            Log.e("Feed Fragment", "Error removing liked post: ${result.error}")
                            Toast.makeText(context, "An unexpected error occurred, please try again", Toast.LENGTH_LONG).show()
                        }
                        else -> {}
                    }
                }
                false -> {
                    //First, add the post to the database, then update the UI
                    when(val result = viewModel.addLikedPost(post)){
                        is Resource.Success -> {
                            adapter.snapshot().getOrNull(position)?.likedByUser = !post.likedByUser
                            adapter.notifyItemChanged(position)
                        }
                        is Resource.Error -> {
                            Log.e("Feed Fragment", "Error adding liked post: ${result.error}")
                            Toast.makeText(context, "An unexpected error occurred, please try again", Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Loading -> {}
                    }
                }
            }
        }
        else if (btnID == "details"){
            BottomSheetMaterialDialog.Builder(requireActivity())
                .setTitle(getString(R.string.details_label))
                .setMessage(HtmlCompat.fromHtml(
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

}