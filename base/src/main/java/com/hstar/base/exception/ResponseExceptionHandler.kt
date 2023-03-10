package com.hstar.base.exception

import io.reactivex.rxjava3.core.*
import retrofit2.HttpException
import java.net.SocketTimeoutException

class ResponseExceptionHandler<T : Any> : SingleTransformer<T, T> {
    override fun apply(upstream: Single<T>): Single<T> =
        upstream.onErrorResumeNext { throwable ->
            when (throwable) {
                is SocketTimeoutException -> {
                    NetworkException.SocketTimeoutException(throwable.message.orEmpty())
                }
                is HttpException -> {
                    when (throwable.code()) {
                        400 -> NetworkException.BadRequestException(throwable.message())
                        401 -> NetworkException.UnauthorizedException(throwable.message())
                        403 -> NetworkException.ForbiddenException(throwable.message())
                        404 -> NetworkException.NotFoundException(throwable.message())
                        408 -> NetworkException.RequestTimeoutException(throwable.message())
                        409 -> NetworkException.ConflictException(throwable.message())
                        429 -> NetworkException.TooManyRequestsException(throwable.message())
                        500 -> NetworkException.InternalServerException(throwable.message())
                        else -> NetworkException.UnknownException(throwable.message())
                    }
                }
                else -> NetworkException.UnknownException(throwable.message.orEmpty())
            }.let { resultException -> Single.error(resultException) }
        }
}

class ResponseExceptionHandlerComplete : CompletableTransformer {
    override fun apply(upstream: Completable): CompletableSource {
        return upstream.onErrorResumeNext { throwable ->
            when (throwable) {
                is SocketTimeoutException -> {
                    NetworkException.SocketTimeoutException(throwable.message.orEmpty())
                }
                is HttpException -> {
                    when (throwable.code()) {
                        400 -> NetworkException.BadRequestException(throwable.message())
                        401 -> NetworkException.UnauthorizedException(throwable.message())
                        403 -> NetworkException.ForbiddenException(throwable.message())
                        404 -> NetworkException.NotFoundException(throwable.message())
                        408 -> NetworkException.RequestTimeoutException(throwable.message())
                        409 -> NetworkException.ConflictException(throwable.message())
                        429 -> NetworkException.TooManyRequestsException(throwable.message())
                        500 -> NetworkException.InternalServerException(throwable.message())
                        else -> NetworkException.UnknownException(throwable.message())
                    }
                }
                else -> NetworkException.UnknownException(throwable.message.orEmpty())
            }.let { resultException -> Completable.error(resultException) }
        }
    }
}


