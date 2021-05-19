package com.ch13mob.testapp.data.mapper

import com.ch13mob.testapp.data.entity.CommentEntity
import com.ch13mob.testapp.domain.model.Comment

object CommentMapper {
    fun toDomain(commentEntity: CommentEntity) =
        Comment(
            id = commentEntity.id,
            postId = commentEntity.postId,
            name = commentEntity.name,
            email = commentEntity.email,
            body = commentEntity.body
        )

}