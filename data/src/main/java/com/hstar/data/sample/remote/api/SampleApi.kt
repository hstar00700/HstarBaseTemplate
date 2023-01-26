package com.hstar.data.sample.remote.api

import com.hstar.data.sample.remote.model.LoginResponse
import com.hstar.data.sample.remote.model.SampleResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface SampleApi {
    //TODO: noAuth 구분
    @GET("api/users")
    fun sampleUserData(): Single<SampleResponse>

    @FormUrlEncoded
    @POST("/api/app/mng/pos/login")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("scope") scope: String,
        @Field("grant_type") grant_type: String
    ): Single<LoginResponse>
}