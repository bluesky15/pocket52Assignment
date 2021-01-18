package com.lkb.assignment.usecase.base


interface UseCaseResponse<Type> {

    fun onSuccess(result: Type)

    fun onError(apiError: String?)
}

