package com.ch13mob.testapp.feature.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ch13mob.testapp.MOCK_IMAGE_URL
import com.ch13mob.testapp.R
import com.ch13mob.testapp.common.extension.loadWithKeepRatio
import com.ch13mob.testapp.databinding.ItemPostBinding
import com.ch13mob.testapp.domain.model.PostItem

typealias PostClickListener = (PostItem) -> Unit
typealias FavouriteClickListener = (Long, Boolean) -> Unit

class PostsAdapter : ListAdapter<PostItem, PostsAdapter.PostViewHolder>(
    ItemDiffCallback()
) {

    var clickListener: PostClickListener = {}
    var favouriteClickListener: FavouriteClickListener = { _: Long, _: Boolean -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemPostBinding.inflate(layoutInflater, parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener, favouriteClickListener)
    }

    override fun onBindViewHolder(
        holder: PostViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            if (payloads[0] == true) {
                holder.bindFavoriteState(getItem(position).isFavourite)
            }
        }
    }

    class PostViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: PostItem,
            clickListener: PostClickListener,
            favouriteClickListener: FavouriteClickListener
        ) {
            binding.tvTitle.text = item.title
            binding.tvBody.text = item.body

            binding.ivPost.loadWithKeepRatio("$MOCK_IMAGE_URL${item.id}")

            binding.ivFavourite.updateFavouriteState(item.isFavourite)
            binding.ivFavourite.setOnClickListener {
                item.isFavourite = !item.isFavourite
                favouriteClickListener(item.id, item.isFavourite)
                binding.ivFavourite.updateFavouriteState(item.isFavourite)
            }

            itemView.setOnClickListener { clickListener(item) }
        }

        fun bindFavoriteState(isFavourite: Boolean) {
            binding.ivFavourite.updateFavouriteState(isFavourite)
        }
    }

    class ItemDiffCallback : DiffUtil.ItemCallback<PostItem>() {
        override fun areItemsTheSame(oldItem: PostItem, newItem: PostItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PostItem, newItem: PostItem): Boolean {
            return oldItem == newItem
        }

        override fun getChangePayload(oldItem: PostItem, newItem: PostItem): Any? {
            return if (oldItem.isFavourite != newItem.isFavourite) true else null
        }
    }
}