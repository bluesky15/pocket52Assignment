package com.lkb.assignment.domain.repository

import com.lkb.assignment.domain.model.Post

interface PostsRepository {
    suspend fun getPosts(): List<Post>
}