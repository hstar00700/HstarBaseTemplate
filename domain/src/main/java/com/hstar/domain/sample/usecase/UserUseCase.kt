package com.hstar.domain.sample.usecase

import com.hstar.domain.user.UserRepository
import javax.inject.Inject

class GetUserDataUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke() = userRepository.getUserData()
}

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    operator fun invoke(userId: String?, pw: String?) = userRepository.signIn(userId ?: "", pw ?: "")
}