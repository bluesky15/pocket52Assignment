package com.lkb.assignment.view.userDetail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lkb.assignment.domain.model.Post
import com.lkb.assignment.domain.model.UserData
import com.lkb.assignment.usecase.GetPostsUseCase
import com.lkb.assignment.usecase.UserDetailUseCase
import com.lkb.assignment.usecase.base.UseCaseResponse
import kotlinx.coroutines.cancel

class UserDetailsViewModel(private val userDetailUseCase: UserDetailUseCase, private val postsUseCase: GetPostsUseCase) : ViewModel() {
    val showProgressbar = MutableLiveData<Boolean>()
    val postList = MutableLiveData<List<Post>>()
    val messageData = MutableLiveData<String>()
    var mUserName = MutableLiveData<String>()
    var mEmail = MutableLiveData<String>()
    fun getUserData(id: String) {
        showProgressbar.value = true
        userDetailUseCase.invoke(viewModelScope, id, object : UseCaseResponse<UserData> {
            override fun onSuccess(result: UserData) {
                mUserName.value = result.name
                mEmail.value = result.email
                Log.d(TAG, result.toString())
            }

            override fun onError(apiError: String?) {
                showProgressbar.value = false
                messageData.value = apiError
            }
        })
        postsUseCase.invoke(viewModelScope, null, object : UseCaseResponse<List<Post>> {
            override fun onSuccess(result: List<Post>) {
                postList.value = result.filter { it.userId==id }
                showProgressbar.value = false
            }

            override fun onError(apiError: String?) {
                messageData.value = apiError
                showProgressbar.value = false
            }

        })
    }

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }

    companion object {
        private val TAG = UserDetailsViewModel::class.java.name
    }
}