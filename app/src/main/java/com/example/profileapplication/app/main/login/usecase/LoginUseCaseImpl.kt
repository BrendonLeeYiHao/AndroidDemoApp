package com.example.profileapplication.app.main.login.usecase

import com.example.profileapplication.app.main.login.repository.LoginRemoteRepo
import com.example.profileapplication.common.ResultWrapper
import javax.inject.Inject

class LoginUseCaseImpl @Inject constructor(
    private val loginRemoteRepo: LoginRemoteRepo
) : LoginUseCase {
    override suspend fun login(name: String): ResultWrapper<String?> {
        return loginRemoteRepo.login(name)
    }
}