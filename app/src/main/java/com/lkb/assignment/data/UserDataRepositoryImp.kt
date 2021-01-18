package com.lkb.baseandroidproject.data

import com.lkb.assignment.domain.model.UserData
import com.lkb.assignment.domain.repository.UserDetailRepository

class UserDataRepositoryImp(private val apiService: ApiService) : UserDetailRepository {
    override suspend fun getUserData(id:String): UserData {
        return apiService.getUserDetails(id)
    }
}