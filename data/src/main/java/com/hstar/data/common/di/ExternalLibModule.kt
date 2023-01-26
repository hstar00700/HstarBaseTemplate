package com.hstar.data.common.di

import android.content.Context
import com.hstar.data.common.utils.SharedPrefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ExternalLibModule {

    @Singleton
    @Provides
    fun provideExternaLib(@ApplicationContext context: Context) : SharedPrefs{
        return SharedPrefs(context)
    }

}