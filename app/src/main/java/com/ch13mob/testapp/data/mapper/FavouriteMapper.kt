package com.ch13mob.testapp.data.mapper

import com.ch13mob.testapp.data.entity.FavouriteEntity

object FavouriteMapper {
    fun toEntity(postId: Long) = FavouriteEntity(postId = postId)
}
