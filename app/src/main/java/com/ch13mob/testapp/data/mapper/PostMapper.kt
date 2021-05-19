package com.ch13mob.testapp.data.mapper

import com.ch13mob.testapp.data.entity.PostEntity
import com.ch13mob.testapp.domain.model.Post

object PostMapper {
    fun toDomain(postEntity: PostEntity) =
        Post(
            id = postEntity.id,
            title = postEntity.title,
            body = postEntity.body,
            userId = postEntity.userId
        )
}