package com.hstar.domain.sample

import com.hstar.domain.sample.entity.SignInEntity
import com.hstar.domain.sample.entity.UserEntity
import io.reactivex.rxjava3.core.Single

interface SampleRepository {
    fun getUserData(): Single<List<UserEntity>>
    fun signIn(id: String, pw: String): Single<SignInEntity>
}