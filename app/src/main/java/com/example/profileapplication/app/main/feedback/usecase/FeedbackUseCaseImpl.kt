package com.example.profileapplication.app.main.feedback.usecase

import com.example.profileapplication.app.main.feedback.repository.FeedbackRemoteRepo
import javax.inject.Inject

class FeedbackUseCaseImpl @Inject constructor(
    private val feedbackRemoteRepo: FeedbackRemoteRepo
): FeedbackUseCase {
    override suspend fun provideFeedback() {
        feedbackRemoteRepo.provideFeedback()
    }

}