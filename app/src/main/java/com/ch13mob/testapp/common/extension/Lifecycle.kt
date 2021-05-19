package com.ch13mob.testapp.common.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T) -> Unit) =
    liveData.observe(this, NonNullObserver(body))

fun <T : Any, L : LiveData<T>> Fragment.observe(liveData: L, body: (T) -> Unit) =
    liveData.observe(viewLifecycleOwner, NonNullObserver(body))

private class NonNullObserver<T : Any>(val body: (T) -> Unit) : Observer<T> {
    override fun onChanged(data: T?) {
        requireNotNull(data)

        body(data)
    }
}
