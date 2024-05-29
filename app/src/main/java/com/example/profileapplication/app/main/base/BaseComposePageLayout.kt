package com.example.profileapplication.app.main.base

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

interface BaseComposePageLayout<VIEW_STATE : BaseViewState, VIEW_MODEL : BaseViewModel<VIEW_STATE,*>> {

    @Composable
    fun CreateComposeLayout(
        viewModel: VIEW_MODEL,
        viewState: VIEW_STATE,
        navController: NavController?,
        content: @Composable ((PaddingValues) -> Unit)?
    )
}