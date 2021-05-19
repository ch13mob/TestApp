package com.ch13mob.testapp.feature

import com.ch13mob.testapp.domain.model.PostItem

interface NavigationListener {
    fun onUserLoggedIn()
    fun onLogout()
    fun showPostDetail(postItem: PostItem)
}