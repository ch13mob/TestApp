package com.ch13mob.testapp.domain.model

data class Post(
    val id: Long,
    val title: String,
    val body: String,
    val userId: Long
)