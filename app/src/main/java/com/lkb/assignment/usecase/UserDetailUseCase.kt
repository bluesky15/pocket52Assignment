package com.lkb.assignment.usecase

import com.lkb.assignment.domain.model.UserData
import com.lkb.assignment.domain.repository.UserDetailRepository
import com.lkb.assignment.usecase.base.UseCase

class UserDetailUseCase(
    private val postsRepository: UserDetailRepository
) : UseCase<UserData, Any?>() {

    override suspend fun run(params: Any?): UserData {
        return postsRepository.getUserData(params as String)
    }
}
