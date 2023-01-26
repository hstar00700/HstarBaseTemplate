package com.hstar.domain.sample.entity

import com.hstar.base.domain.model.Entity

data class UserEntity(
    val avatar: String = "",
    val email: String = "없음",
    val firstName: String = "",
    val id: Int = -1,
    val lastName: String = ""
) : Entity