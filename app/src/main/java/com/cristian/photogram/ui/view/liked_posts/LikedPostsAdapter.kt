package com.cristian.photogram.ui.view.liked_posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.cristian.photogram.databinding.PostItemBinding
import com.cristian.photogram.domain.model.Post

class LikedPostsAdapter(
    private val postClickListener: (post: Post, btnID: String) -> Unit) : PagingDataAdapter<Post, LikedPostsViewHolder>(PostDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikedPostsViewHolder {
        return LikedPostsViewHolder(PostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: LikedPostsViewHolder, position: Int) {
        getItem(position).let {
            holder.bind(it!!, postClickListener)
        }
    }
}

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}