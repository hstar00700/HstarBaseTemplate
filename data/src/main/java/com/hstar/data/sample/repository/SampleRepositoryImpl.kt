package com.hstar.data.sample.repository

import com.hstar.data.sample.remote.source.RemoteDataSource
import com.hstar.data.model.UserDataModel
import com.hstar.domain.sample.SampleRepository
import com.hstar.domain.sample.entity.SignInEntity
import com.hstar.domain.sample.entity.UserEntity
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class SampleRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
//    private val localDataSource: LocalDataSource
) : SampleRepository {

    override fun getUserData(): Single<List<UserEntity>> {
        return remoteDataSource
            .getUserData()
            .map { it.map(UserDataModel::toEntity) }
    }

    override fun signIn(id: String, pw: String): Single<SignInEntity> {
        return remoteDataSource
            .signIn(id, pw)
            .map {
                SignInEntity(
                    access_token = it.access_token,
                    token_type = it.token_type,
                    refresh_token = it.refresh_token,
                    expires_in = it.expires_in,
                    scope = it.scope,
                    jti = it.jti,
                    firebaseToken = it.firebaseToken,
                    storeCd = it.storeCd,
                    storeId = it.storeId,
                    custCd = it.custCd,
                    custId = it.custId,
                    authRoles = it.authRoles,
                    storeMng = it.storeMng,
                    admin = it.admin
                )
            }
    }
}