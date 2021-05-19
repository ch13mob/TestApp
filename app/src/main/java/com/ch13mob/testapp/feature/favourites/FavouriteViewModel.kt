package com.ch13mob.testapp.feature.favourites

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch13mob.testapp.domain.error.Error
import com.ch13mob.testapp.domain.model.PostItem
import com.ch13mob.testapp.domain.usecase.DeleteFavouritePostUseCase
import com.ch13mob.testapp.domain.usecase.GetAllFavouritePostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val getAllFavouritePostsUseCase: GetAllFavouritePostsUseCase,
    private val deleteFavouritePostUseCase: DeleteFavouritePostUseCase
) : ViewModel(), LifecycleObserver {

    sealed class State {
        object Loading : State()
        data class Favourites(val posts: List<PostItem>) : State()
        data class Fail(val error: Error) : State()
    }

    val state = MutableLiveData<State>()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun getFavouritePosts() {
        viewModelScope.launch {
            state.value = State.Loading
            getAllFavouritePostsUseCase.launch().fold(
                onSuccess = {
                    state.value = State.Favourites(it)
                },
                onFailure = { state.value = State.Fail(it) }
            )
        }
    }

    fun onFavouriteClicked(postId: Long) {
        viewModelScope.launch {
            deleteFavouritePostUseCase.launch(postId).fold(
                onSuccess = {
                    getFavouritePosts()
                },
                onFailure = { state.value = State.Fail(it) }
            )
        }
    }
}