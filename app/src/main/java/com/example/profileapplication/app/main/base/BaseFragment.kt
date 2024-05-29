package com.example.profileapplication.app.main.base

import androidx.compose.runtime.Composable
import androidx.fragment.app.Fragment
import androidx.navigation.NavController

abstract class BaseFragment<VIEW_STATE : BaseViewState, VIEW_MODEL : BaseViewModel<VIEW_STATE,*>> : Fragment() {

    @Composable
    abstract fun SetComposeContent()

    @Composable
    abstract fun BuildContent(viewState: VIEW_STATE, navController: NavController?)

    abstract fun onViewEvent(viewEvent: BaseViewEvent)

    abstract val onViewModel: VIEW_MODEL
}