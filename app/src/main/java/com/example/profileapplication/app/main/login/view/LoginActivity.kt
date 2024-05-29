package com.example.profileapplication.app.main.login.view

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.example.profileapplication.app.main.base.BaseActivity
import com.example.profileapplication.app.main.base.BaseViewEvent
import com.example.profileapplication.app.main.login.view.ui.LoginActivityLayout
import com.example.profileapplication.app.main.login.viewmodel.LoginViewEvent
import com.example.profileapplication.app.main.login.viewmodel.LoginViewModel
import com.example.profileapplication.app.main.login.viewmodel.LoginViewState
import com.example.profileapplication.navigation.NavigateToKey
import com.example.profileapplication.navigation.Navigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<LoginViewState, LoginViewModel>() {

    companion object {
        private const val TAG = "LoginActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SetComposeContent()
        }
    }

    private val viewModel: LoginViewModel by viewModels()

    override val onViewModel: LoginViewModel
        get() = viewModel

    @Composable
    override fun SetComposeContent() {
        viewModel.viewState.observeAsState().value?.let { nonNullViewState ->
            BuildContent(viewState = nonNullViewState)
        } ?: run {
            Log.e(TAG, "ViewState cannot be null!")
        }

        viewModel.viewEvent.observeAsState().value?.let {
            onViewEvent(it)
        }
    }

    @Composable
    override fun BuildContent(viewState: LoginViewState) {
        LoginActivityLayout.CONTENT.CreateComposeLayout(
            viewModel = viewModel,
            viewState = viewState,
            navController = null,
            content = null
        )
    }

    override fun onViewEvent(viewEvent: BaseViewEvent) {
        when (viewEvent) {
            is LoginViewEvent.NavigateToMain -> {
                Navigator.performNavigation(this, null,  NavigateToKey.App.MainHostActivity)
            }
            is LoginViewEvent.NavigateToRegistration -> {
                Navigator.performNavigation(this, null,  NavigateToKey.App.AccountActivity)
                finish()
            }
        }
    }
}