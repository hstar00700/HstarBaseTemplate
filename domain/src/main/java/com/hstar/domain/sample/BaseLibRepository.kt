package com.hstar.domain.sample

import com.hstar.domain.sample.entity.UserEntity
import io.reactivex.rxjava3.core.Single

interface BaseLibRepository {
    fun getUserData(): Single<List<UserEntity>>
}