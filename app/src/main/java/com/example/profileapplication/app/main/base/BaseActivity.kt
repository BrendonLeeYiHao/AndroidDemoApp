package com.example.profileapplication.app.main.base

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable

abstract class BaseActivity<VIEW_STATE : BaseViewState, VIEW_MODEL : BaseViewModel<VIEW_STATE,*>> : ComponentActivity() {

    @Composable
    abstract fun SetComposeContent()

    @Composable
    abstract fun BuildContent(viewState: VIEW_STATE)

    abstract fun onViewEvent(viewEvent: BaseViewEvent)

    abstract val onViewModel: VIEW_MODEL
}