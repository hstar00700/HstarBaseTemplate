package com.hstar.domain.sample.usecase

import com.hstar.domain.user.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke() = userRepository.getUserData()
}