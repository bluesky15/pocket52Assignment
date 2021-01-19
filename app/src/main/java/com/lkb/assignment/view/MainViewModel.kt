package com.lkb.assignment.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lkb.assignment.domain.model.Post
import com.lkb.assignment.domain.model.PostCount
import com.lkb.assignment.usecase.GetPostsUseCase
import com.lkb.assignment.usecase.base.UseCaseResponse
import kotlinx.coroutines.cancel

class MainViewModel(private val postUseCase: GetPostsUseCase) : ViewModel() {
    val postsData = MutableLiveData<List<PostCount>>()
    val postList = MutableLiveData<List<Post>>()
    val showProgressbar = MutableLiveData<Boolean>()
    val messageData = MutableLiveData<String>()
    val dataNotFound = MutableLiveData<Boolean>()
    var temp: List<PostCount>? = null
    fun getPost() {
        showProgressbar.value = true
        postUseCase.invoke(viewModelScope, null, object : UseCaseResponse<List<Post>> {
            override fun onSuccess(result: List<Post>) {
                postList.value = result
                var postCountList = mutableListOf<PostCount>()
                val allUserId = result.map {
                    it.userId
                }.distinctBy { it }
                var count = 0
                allUserId.forEach {
                    result.forEach { e -> if (e.userId == it) count++ }
                    postCountList.add(PostCount(it, count.toString()))
                    count = 0
                }
                postsData.value = postCountList
                temp = postCountList
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

    fun filterData(s: CharSequence?) {
        if (s?.isEmpty() == true) {
            postsData.value = temp
        } else {
            val filteredList = temp?.filter { it.userId.contentEquals(s.toString()) }
            postsData.value = filteredList
            filteredList?.let { dataNotFound.value = it.isEmpty() }
        }
    }

    companion object {
        private val TAG = MainViewModel::class.java.name
    }
}