package com.ch13mob.testapp.feature.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ch13mob.testapp.R
import com.ch13mob.testapp.common.extension.observe
import com.ch13mob.testapp.databinding.FragmentPostsBinding
import com.ch13mob.testapp.domain.model.PostItem
import com.ch13mob.testapp.feature.NavigationListener
import com.ch13mob.testapp.feature.posts.PostsViewModel.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostsFragment : Fragment() {

    private val navigationListener get() = activity as NavigationListener

    private val viewModel by activityViewModels<PostsViewModel>()

    private var _binding: FragmentPostsBinding? = null
    private val binding get() = _binding!!

    private val adapter = PostsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        lifecycle.addObserver(viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.apply {
            clickListener = ::onPostClick
            favouriteClickListener = ::onFavouriteClick
        }

        binding.rvPosts.adapter = adapter

        observe(viewModel.state) {
            when (it) {
                is State.Logout -> {
                    navigationListener.onLogout()
                }
                is State.Loading -> {
                    setLoading(true)
                }
                is State.Posts -> {
                    setLoading(false)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                viewModel.logout()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.pbPosts.isVisible = true
        } else {
            binding.pbPosts.isGone = true
        }
    }

    private fun onPostClick(postItem: PostItem) {
        navigationListener.showPostDetail(postItem)
    }

    private fun onFavouriteClick(postId: Long, isFavourite: Boolean) {
        viewModel.onFavouriteClicked(postId, isFavourite)
    }
}