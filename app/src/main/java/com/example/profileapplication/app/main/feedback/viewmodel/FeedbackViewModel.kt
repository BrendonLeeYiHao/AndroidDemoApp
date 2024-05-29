package com.example.profileapplication.app.main.feedback.viewmodel

import com.example.profileapplication.app.main.base.BaseViewModel
import com.example.profileapplication.app.main.feedback.usecase.FeedbackUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedbackViewModel @Inject constructor(
    private val feedbackUseCase: FeedbackUseCase
): BaseViewModel<FeedbackViewState, FeedbackViewEvent>() {

    init {
        mutableViewState.value = FeedbackViewState()
    }
}