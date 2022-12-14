package com.cristian.photogram.ui.view.liked_posts

import android.icu.text.SimpleDateFormat
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cristian.photogram.R
import com.cristian.photogram.databinding.PostItemBinding
import com.cristian.photogram.domain.model.Post
import java.util.*

class LikedPostsViewHolder(private val binding: PostItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post, postClickListener: (post: Post, btnID: String) -> Unit) {
        with(binding) {
            authorNameTxt.text = post.user.username
            likeCountTxt.text = root.context.getString(R.string.likesTxt, post.likes)
            postDescriptionTxt.text = HtmlCompat.fromHtml("<b>${post.user.username}</b> ${post.description?: root.context.getString(R.string.post_no_description)}", HtmlCompat.FROM_HTML_MODE_COMPACT)
            postDateTxt.text = timeAgo(post.createdAt)
            Glide.with(postContentIv.context)
                .load(post.photoUrl)
                .into(postContentIv)
            Glide.with(authorAvatarIv.context)
                .load(post.user.profilePictureUrl)
                .circleCrop()
                .into(authorAvatarIv)
            detailsIv.setOnClickListener { postClickListener(post, "details") }
            with(likeBtn){
                setBackgroundResource(R.drawable.heart_active)
                setOnClickListener {
                    setBackgroundResource(R.drawable.heart)
                    postClickListener(post, "like")
                }
            }
        }
    }

    private fun timeAgo(date: String): String{
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val dateTime = formatter.parse(date)
        val now = Calendar.getInstance().time
        val diff = now.time - dateTime.time
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        val weeks = days / 7
        val months = weeks / 4
        val years = months / 12
        with(binding.root.resources){
            return when {
                years  > 0 -> this.getQuantityString(R.plurals.years_plural, years.toInt(), years.toInt())
                months > 0 -> this.getQuantityString(R.plurals.months_plural, months.toInt(), months.toInt())
                weeks  > 0 -> this.getQuantityString(R.plurals.weeks_plural, weeks.toInt(), weeks.toInt())
                days   > 0 -> this.getQuantityString(R.plurals.days_plural, days.toInt(), days.toInt())
                hours  > 0 -> this.getQuantityString(R.plurals.hours_plural, hours.toInt(), hours.toInt())
                minutes > 0 -> this.getQuantityString(R.plurals.minutes_plural, minutes.toInt(), minutes.toInt())
                seconds > 0 -> this.getQuantityString(R.plurals.seconds_plural, seconds.toInt(), seconds.toInt())
                else -> this.getString(R.string.just_now)
            }
        }
    }
}