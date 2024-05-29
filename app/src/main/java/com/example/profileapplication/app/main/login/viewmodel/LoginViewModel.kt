package com.example.profileapplication.app.main.login.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.profileapplication.app.main.base.BaseViewModel
import com.example.profileapplication.app.main.login.usecase.LoginUseCase
import com.example.profileapplication.common.Constants
import com.example.profileapplication.common.ResultWrapper
import com.example.profileapplication.di.datastore.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val dataStoreManager: DataStoreManager
) : BaseViewModel<LoginViewState, LoginViewEvent>() {

    companion object{
        private const val TAG = "LoginViewModel"
    }

    init {
        mutableViewState.value = LoginViewState()
        viewModelScope.launch {
            val username = dataStoreManager.getUsername()
            if (username != null) {
                mutableViewState.value = mutableViewState.value?.copy(
                    username = username
                )
               navigate(LoginViewEvent.NavigateToMain)
            }
        }
    }

    fun updateUsername(name: String) {
        mutableViewState.value = mutableViewState.value?.copy(
            username = name
        )
    }

    fun login() {
        viewModelScope.launch {
            val result = loginUseCase.login(viewState.value!!.username)
            when (result) {
                is ResultWrapper.Success -> {
                    dataStoreManager.saveUsername(viewState.value!!.username)
                    navigate(LoginViewEvent.NavigateToMain)
                }

                is ResultWrapper.Error -> {
                    val errorMessage = Constants.LOGIN_ERROR
                    mutableViewState.value = mutableViewState.value?.copy(
                        messageTitle = Constants.ERROR,
                        messageBody = errorMessage,
                        showAlertDialog = true
                    )
                }
            }
        }
    }

    fun toggleAlertDialog() {
        mutableViewState.value = mutableViewState.value?.copy(
            showAlertDialog = !viewState.value!!.showAlertDialog,
            messageTitle = Constants.EMPTY,
            messageBody = Constants.EMPTY
        )
    }

    fun navigateToRegistration() {
        navigate(LoginViewEvent.NavigateToRegistration)
    }
}