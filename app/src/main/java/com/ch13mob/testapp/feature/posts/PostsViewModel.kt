package com.ch13mob.testapp.feature.posts

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch13mob.testapp.domain.error.Error
import com.ch13mob.testapp.domain.model.PostItem
import com.ch13mob.testapp.domain.usecase.DeleteFavouritePostUseCase
import com.ch13mob.testapp.domain.usecase.GetPostsUseCase
import com.ch13mob.testapp.domain.usecase.LogoutUseCase
import com.ch13mob.testapp.domain.usecase.SaveFavouritePostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val saveFavouritePostUseCase: SaveFavouritePostUseCase,
    private val deleteFavouritePostUseCase: DeleteFavouritePostUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel(), LifecycleObserver {

    sealed class State {
        object Loading : State()
        object Logout : State()
        data class Posts(val posts: List<PostItem>) : State()
        data class Fail(val error: Error) : State()
    }

    val state = MutableLiveData<State>()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun getPosts() {
        viewModelScope.launch {
            state.value = State.Loading
            getPostsUseCase.launch().fold(
                onSuccess = {
                    state.value = State.Posts(it)
                },
                onFailure = { state.value = State.Fail(it) }
            )
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase.launch().fold(
                onSuccess = {
                    state.value = State.Logout
                },
                onFailure = { state.value = State.Fail(it) }
            )
        }
    }

    fun onFavouriteClicked(postId: Long, isFavourite: Boolean) {
        if (isFavourite) {
            saveFavouritePost(postId)
        } else {
            deleteFavouritePost(postId)
        }
    }

    private fun saveFavouritePost(postId: Long) {
        viewModelScope.launch {
            saveFavouritePostUseCase.launch(postId).fold(
                onSuccess = {},
                onFailure = { state.value = State.Fail(it) }
            )
        }
    }

    private fun deleteFavouritePost(postId: Long) {
        viewModelScope.launch {
            deleteFavouritePostUseCase.launch(postId).fold(
                onSuccess = {},
                onFailure = { state.value = State.Fail(it) }
            )
        }
    }
}