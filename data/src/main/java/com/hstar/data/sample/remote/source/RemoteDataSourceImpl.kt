package com.hstar.data.sample.remote.source

import com.hstar.base.exception.ResponseExceptionHandler
import com.hstar.data.model.UserDataModel
import com.hstar.data.sample.remote.api.SampleApi
import com.hstar.data.sample.remote.model.Data
import com.hstar.data.sample.remote.model.LoginResponse
import io.reactivex.rxjava3.core.Single

class RemoteDataSourceImpl constructor(
    private val sampleApi: SampleApi,
) : RemoteDataSource {
    override fun getUserData(): Single<List<UserDataModel>> {
        return sampleApi
            .sampleUserData()
            .map { it.data.map(Data::toDataModel) }
            .compose(ResponseExceptionHandler())
    }

    override fun signIn(id: String, pw: String): Single<LoginResponse> {
        return sampleApi
            .login(username = id, password = pw, scope = "app", grant_type = "password")
            .compose(ResponseExceptionHandler())
    }
}