package com.lkb.baseandroidproject.data

import com.lkb.assignment.domain.model.Post
import com.lkb.assignment.domain.model.UserData
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/posts")
    suspend fun getPosts(): List<Post>

    @GET("/users/{id}")
    suspend fun getUserDetails(@Path("id") id:String): UserData
}