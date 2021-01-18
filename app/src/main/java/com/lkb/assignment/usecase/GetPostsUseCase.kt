package com.lkb.assignment.usecase


import com.lkb.assignment.domain.model.Post
import com.lkb.assignment.domain.repository.PostsRepository
import com.lkb.assignment.usecase.base.UseCase

class GetPostsUseCase constructor(
    private val postsRepository: PostsRepository
) : UseCase<List<Post>, Any?>() {

    override suspend fun run(params: Any?): List<Post> {
        return postsRepository.getPosts()
    }

}