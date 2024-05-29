package com.example.profileapplication.app.main.login.viewmodel

import com.example.profileapplication.app.main.base.BaseViewEvent

sealed class LoginViewEvent: BaseViewEvent {

    data object NavigateToMain: LoginViewEvent()
    data object NavigateToRegistration: LoginViewEvent()
}