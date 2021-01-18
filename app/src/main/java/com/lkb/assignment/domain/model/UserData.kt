package com.lkb.assignment.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserData(val name: String, val email: String)