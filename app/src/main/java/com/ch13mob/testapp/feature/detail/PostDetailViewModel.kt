package com.ch13mob.testapp.feature.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch13mob.testapp.domain.error.Error
import com.ch13mob.testapp.domain.model.Comment
import com.ch13mob.testapp.domain.usecase.DeleteFavouritePostUseCase
import com.ch13mob.testapp.domain.usecase.GetCommentsByPostIdUseCase
import com.ch13mob.testapp.domain.usecase.SaveFavouritePostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val getCommentsByPostIdUseCase: GetCommentsByPostIdUseCase,
    private val saveFavouritePostUseCase: SaveFavouritePostUseCase,
    private val deleteFavouritePostUseCase: DeleteFavouritePostUseCase
) : ViewModel() {

    sealed class State {
        object Loading : State()
        data class Comments(val posts: List<Comment>) : State()
        data class Fail(val error: Error) : State()
    }

    val state = MutableLiveData<State>()

    fun getCommentsByPostId(postId: Long) {
        viewModelScope.launch {
            state.value = State.Loading
            getCommentsByPostIdUseCase.launch(postId).fold(
                onSuccess = {
                    state.value = State.Comments(it)
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