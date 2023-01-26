package com.hstar.data.sample

import com.hstar.data.common.di.NetworkModule
import com.hstar.data.common.di.Remote
import com.hstar.data.sample.remote.api.SampleApi
import com.hstar.data.sample.remote.source.RemoteDataSourceImpl
import com.hstar.data.sample.repository.SampleRepositoryImpl
import com.hstar.data.sample.repository.UserRepositoryImpl
import com.hstar.domain.sample.SampleRepository
import com.hstar.domain.user.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
object SampleModule {

    @Singleton
    @Provides
    fun provideSampleApi(retrofit: Retrofit): SampleApi {
        return retrofit.create(SampleApi::class.java)
    }

    @Remote
    @Provides
    fun provideRemoteDataSource(
        sampleApi: SampleApi,
    ): RemoteDataSourceImpl = RemoteDataSourceImpl(sampleApi)

    @Singleton
    @Provides
    fun provideSampleRepository(
        @Remote remoteDataSource: RemoteDataSourceImpl
//        @Local localDataSource: LocalDataSource
    ): SampleRepository {
        return SampleRepositoryImpl(remoteDataSource)
    }

    @Singleton
    @Provides
    fun provideUserRepository(
        @Remote remoteDataSource: RemoteDataSourceImpl
    ): UserRepository {
        return UserRepositoryImpl(remoteDataSource)
    }
}