package com.lkb.assignment.di

import com.lkb.assignment.view.MainViewModel
import com.lkb.assignment.view.userDetail.UserDetailsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val AppModule = module {

    viewModel { MainViewModel(get()) }

    viewModel { UserDetailsViewModel(get(),get()) }

    single { createGetPostsUseCase(get()) }

    single { createPostRepository(get()) }

    single { createGetUserDetailUseCase(get()) }

    single { createUserDetailRepository(get()) }
}