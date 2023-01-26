package com.hstar.domain.sample

import com.hstar.domain.sample.entity.UserEntity
import io.reactivex.rxjava3.core.Single

interface BaseLibRepository {
    //TODO: coroutine
    fun getUserData(): Single<List<UserEntity>>
}