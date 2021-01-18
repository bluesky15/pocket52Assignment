package com.lkb.baseandroidproject.data

import com.lkb.assignment.domain.model.Post
import com.lkb.assignment.domain.repository.PostsRepository

class PostsRepositoryImp(private val apiService: ApiService) : PostsRepository {
    override suspend fun getPosts(): List<Post> {
        return apiService.getPosts()
    }
}