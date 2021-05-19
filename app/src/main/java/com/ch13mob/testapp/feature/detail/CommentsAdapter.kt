package com.ch13mob.testapp.feature.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ch13mob.testapp.common.extension.simpleDiffCallback
import com.ch13mob.testapp.databinding.ItemCommentBinding
import com.ch13mob.testapp.domain.model.Comment

class CommentsAdapter : ListAdapter<Comment, CommentsAdapter.CommentViewHolder>(
    simpleDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCommentBinding.inflate(layoutInflater, parent, false)
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CommentViewHolder(private val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Comment) {
            binding.tvEmail.text = item.email
            binding.tvName.text = item.name
            binding.tvBody.text = item.body
        }
    }
}