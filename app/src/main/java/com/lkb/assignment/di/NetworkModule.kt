package com.lkb.assignment.di

import com.lkb.assignment.BuildConfig
import com.lkb.assignment.usecase.GetPostsUseCase
import com.lkb.assignment.domain.repository.PostsRepository
import com.lkb.baseandroidproject.data.ApiService
import com.lkb.baseandroidproject.data.PostsRepositoryImp
import com.lkb.baseandroidproject.data.UserDataRepositoryImp
import com.lkb.assignment.domain.repository.UserDetailRepository
import com.lkb.assignment.usecase.UserDetailUseCase
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val TIME_OUT = 30L

val NetworkModule = module {

    single { createService(get()) }

    single { createRetrofit(get(), BuildConfig.BASE_URL) }

    single { createOkHttpClient() }

    single { MoshiConverterFactory.create() }

    single { Moshi.Builder().build() }

}

fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    return OkHttpClient.Builder()
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor).build()
}

fun createRetrofit(okHttpClient: OkHttpClient, url: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create()).build()
}

fun createService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
}

fun createPostRepository(apiService: ApiService): PostsRepository {
    return PostsRepositoryImp(apiService)
}

fun createGetPostsUseCase(postsRepository: PostsRepository): GetPostsUseCase {
    return GetPostsUseCase(postsRepository)
}

fun createUserDetailRepository(apiService: ApiService): UserDetailRepository {
    return UserDataRepositoryImp(apiService)
}

fun createGetUserDetailUseCase(userDetailRepository: UserDetailRepository): UserDetailUseCase {
    return UserDetailUseCase(userDetailRepository)
}
