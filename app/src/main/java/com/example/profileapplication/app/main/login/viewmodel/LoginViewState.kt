package com.example.profileapplication.app.main.login.viewmodel

import com.example.profileapplication.app.main.base.BaseViewState
import com.example.profileapplication.common.Constants

data class LoginViewState(
    val isLoading: Boolean = false,
    val username: String = Constants.EMPTY,
    val password: String = Constants.EMPTY,
    val messageTitle: String = Constants.EMPTY,
    val messageBody: String = Constants.EMPTY,
    val showAlertDialog: Boolean = false
): BaseViewState