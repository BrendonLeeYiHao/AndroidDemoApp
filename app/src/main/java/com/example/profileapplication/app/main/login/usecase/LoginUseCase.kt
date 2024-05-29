package com.example.profileapplication.app.main.login.usecase

import com.example.profileapplication.common.ResultWrapper

interface LoginUseCase {
    suspend fun login(name: String): ResultWrapper<String?>
}