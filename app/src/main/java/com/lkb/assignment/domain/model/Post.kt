package com.lkb.assignment.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Post(
    val userId: String,
    val id: String,
    val title: String,
    val body: String
)