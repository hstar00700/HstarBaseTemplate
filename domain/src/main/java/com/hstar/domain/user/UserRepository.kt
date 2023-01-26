package com.hstar.domain.user

import com.hstar.domain.sample.entity.SignInEntity
import com.hstar.domain.sample.entity.UserEntity
import io.reactivex.rxjava3.core.Single

interface UserRepository {
    //TODO: coroutine
    fun getUserData(): Single<List<UserEntity>>

    fun signIn(id: String, pw: String): Single<SignInEntity>
}