package com.example.profileapplication.app.main.login.repository

import com.example.profileapplication.common.ResultWrapper
import com.example.profileapplication.common.handleApiResponse
import com.example.profileapplication.network.ApiService
import javax.inject.Inject

class LoginRemoteRepoImpl @Inject constructor(
    private val apiService: ApiService
) : LoginRemoteRepo {
    override suspend fun login(name: String): ResultWrapper<String?> {
        return handleApiResponse { apiService.login(name) }
    }
}