package com.example.profileapplication.app.main.login.repository

import com.example.profileapplication.common.ResultWrapper

interface LoginRemoteRepo {
    suspend fun login(name: String): ResultWrapper<String?>
}