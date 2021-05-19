package com.ch13mob.testapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostItem(
    val id: Long,
    val title: String,
    val body: String,
    val userId: Long,
    var isFavourite: Boolean,
) : Parcelable