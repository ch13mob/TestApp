package com.ch13mob.testapp.feature.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ch13mob.testapp.MOCK_IMAGE_URL
import com.ch13mob.testapp.POST_ITEM_KEY
import com.ch13mob.testapp.R
import com.ch13mob.testapp.common.extension.addOffsetListener
import com.ch13mob.testapp.common.extension.loadWithKeepRatio
import com.ch13mob.testapp.common.extension.observe
import com.ch13mob.testapp.databinding.FragmentPostDetailBinding
import com.ch13mob.testapp.domain.model.PostItem
import com.ch13mob.testapp.feature.detail.PostDetailViewModel.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailFragment : Fragment() {

    private val viewModel by activityViewModels<PostDetailViewModel>()

    private var _binding: FragmentPostDetailBinding? = null
    private val binding get() = _binding!!

    private val adapter = CommentsAdapter()

    private val postItem: PostItem?
        get() = arguments?.getParcelable(
            POST_ITEM_KEY
        )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.appbarPostDetail.addOffsetListener { alpha ->
            binding.ivPost.alpha = alpha
            binding.tvTitle.alpha = alpha
            binding.ivFavourite.alpha = alpha
            binding.tvBody.alpha = alpha
        }


        postItem?.let { postItem ->
            binding.ivPost.loadWithKeepRatio("$MOCK_IMAGE_URL${postItem.id}")
            binding.tvTitle.text = postItem.title
            binding.tvBody.text = postItem.body

            binding.ivFavourite.updateFavouriteState(postItem.isFavourite)
            binding.ivFavourite.setOnClickListener {
                postItem.isFavourite = !postItem.isFavourite
                binding.ivFavourite.updateFavouriteState(postItem.isFavourite)
                viewModel.onFavouriteClicked(postItem.id, postItem.isFavourite)
            }
        }

        binding.rvComments.adapter = adapter

        observe(viewModel.state) {
            when (it) {
                is State.Loading -> {
                    setLoading(true)
                }
                is State.Comments -> {
                    setLoading(false)
                    adapter.submitList(it.posts)
                }
                is State.Fail -> {
                    setLoading(false)
                    Toast.makeText(requireContext(), it.error.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }

        postItem?.let { viewModel.getCommentsByPostId(it.id) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.pbComments.isVisible = true
        } else {
            binding.pbComments.isGone = true
        }
    }
}