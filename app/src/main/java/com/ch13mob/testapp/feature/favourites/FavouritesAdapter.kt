package com.ch13mob.testapp.feature.favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ch13mob.testapp.MOCK_IMAGE_URL
import com.ch13mob.testapp.R
import com.ch13mob.testapp.common.extension.loadWithKeepRatio
import com.ch13mob.testapp.common.extension.simpleDiffCallback
import com.ch13mob.testapp.databinding.ItemPostBinding
import com.ch13mob.testapp.domain.model.PostItem

typealias PostClickListener = (PostItem) -> Unit
typealias FavouriteClickListener = (Long) -> Unit

class FavouritesAdapter : ListAdapter<PostItem, FavouritesAdapter.PostViewHolder>(
    simpleDiffCallback()
) {

    var clickListener: PostClickListener = {}
    var favouriteClickListener: FavouriteClickListener = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemPostBinding.inflate(layoutInflater, parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener, favouriteClickListener)
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
                favouriteClickListener(item.id)
            }

            itemView.setOnClickListener { clickListener(item) }
        }
    }
}