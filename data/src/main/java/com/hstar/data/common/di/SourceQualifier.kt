package com.hstar.data.common.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Remote

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Local
