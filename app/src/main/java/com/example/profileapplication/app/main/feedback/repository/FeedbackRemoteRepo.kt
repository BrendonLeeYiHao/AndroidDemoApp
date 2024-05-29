package com.example.profileapplication.app.main.feedback.repository

interface FeedbackRemoteRepo {
    suspend fun provideFeedback()
}