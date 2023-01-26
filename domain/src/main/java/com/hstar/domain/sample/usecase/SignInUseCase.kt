package com.hstar.domain.sample.usecase

import com.hstar.domain.user.UserRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    fun signIn(id: String, pw: String) = userRepository.signIn(id, pw)
}