package com.hstar.presentation.comon

import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineExceptionHandler

val globalChe = CoroutineExceptionHandler { _, error ->
    Logger.e("Error : $error")
}