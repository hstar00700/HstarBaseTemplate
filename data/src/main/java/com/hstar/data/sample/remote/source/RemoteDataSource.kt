package com.hstar.data.sample.remote.source

import com.hstar.data.model.UserDataModel
import com.hstar.data.sample.remote.model.LoginResponse
import io.reactivex.rxjava3.core.Single

interface RemoteDataSource {
    /**
     * 사용자 정보
     */
    // 사용자 정보
    fun getUserData(): Single<List<UserDataModel>>
    fun signIn(id: String, pw: String): Single<LoginResponse>
}