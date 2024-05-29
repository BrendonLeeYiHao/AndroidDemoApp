package com.example.profileapplication.app.main.feedback.repository

import com.example.profileapplication.network.ApiService
import javax.inject.Inject

class FeedbackRemoteRepoImpl @Inject constructor(
    private val apiService: ApiService
): FeedbackRemoteRepo {
    override suspend fun provideFeedback() {
        apiService.getAllProfile()
    }
}