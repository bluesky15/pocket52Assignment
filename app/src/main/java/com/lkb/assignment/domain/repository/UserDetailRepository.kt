package com.lkb.assignment.domain.repository

import com.lkb.assignment.domain.model.UserData

interface UserDetailRepository {
    suspend fun getUserData(id:String): UserData
}
