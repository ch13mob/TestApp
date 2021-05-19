package com.ch13mob.testapp.feature.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ch13mob.testapp.common.extension.observe
import com.ch13mob.testapp.databinding.FragmentFavouritesBinding
import com.ch13mob.testapp.domain.model.PostItem
import com.ch13mob.testapp.feature.NavigationListener
import com.ch13mob.testapp.feature.favourites.FavouriteViewModel.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritePostsFragment : Fragment() {

    private val navigationListener get() = activity as NavigationListener

    private val viewModel by activityViewModels<FavouriteViewModel>()

    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!

    private val adapter = FavouritesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycle.addObserver(viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.apply {
            clickListener = ::onPostClick
            favouriteClickListener = ::onFavouriteClick
        }

        binding.rvFavourites.adapter = adapter

        observe(viewModel.state) {
            when (it) {
                is State.Loading -> {
                    setLoading(true)
                }
                is State.Favourites -> {
                    setLoading(false)

                    if (it.posts.isEmpty()) {
                        binding.tvEmptyFavourites.isVisible = true
                    } else {
                        binding.tvEmptyFavourites.isGone = true
                    }

                    adapter.submitList(it.posts)
                }
                is State.Fail -> {
                    setLoading(false)
                    Toast.makeText(requireContext(), it.error.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.pbFavourites.isVisible = true
        } else {
            binding.pbFavourites.isGone = true
        }
    }

    private fun onPostClick(postItem: PostItem) {
        navigationListener.showPostDetail(postItem)
    }

    private fun onFavouriteClick(postId: Long) {
        viewModel.onFavouriteClicked(postId)
    }
}