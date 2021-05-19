package com.ch13mob.testapp.feature

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ch13mob.testapp.POST_ITEM_KEY
import com.ch13mob.testapp.R
import com.ch13mob.testapp.common.extension.observe
import com.ch13mob.testapp.databinding.ActivityMainBinding
import com.ch13mob.testapp.domain.model.PostItem
import com.ch13mob.testapp.feature.MainViewModel.State
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationListener {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    private val navController: NavController
        get() = findNavController(R.id.nav_host_fragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        navController.apply {
            addOnDestinationChangedListener { _, destination, _ ->
                if (destination.id == R.id.postDetailFragment ||
                    destination.id == R.id.loginFragment
                ) {
                    binding.navView.isGone = true
                } else {
                    binding.navView.isVisible = true
                }
            }
        }

        val appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(
                R.id.postsFragment, R.id.favouritesFragment, R.id.loginFragment
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        observe(viewModel.state) {
            when (it) {
                is State.IsUserLoggedIn -> {
                    if (it.isUserLoggedIn) {
                        navigate(R.id.actionPostsFragment)
                    } else {
                        navigate(R.id.actionLoginFragment)
                    }
                }
                is State.Fail -> {
                    Toast.makeText(this, it.error.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    override fun onUserLoggedIn() {
        navigate(R.id.actionPostsFragment)
    }

    override fun onLogout() {
        finish()
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun showPostDetail(postItem: PostItem) {
        val bundle = bundleOf(POST_ITEM_KEY to postItem)
        navigate(R.id.actionPostDetailFragment, bundle)
    }

    private fun navigate(id: Int) = navController.navigate(id)

    private fun navigate(id: Int, bundle: Bundle) = navController.navigate(id, bundle)
}