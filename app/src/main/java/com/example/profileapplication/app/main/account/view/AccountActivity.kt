package com.example.profileapplication.app.main.account.view

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.example.profileapplication.app.main.account.view.ui.AccountActivityLayout
import com.example.profileapplication.app.main.base.BaseActivity
import com.example.profileapplication.app.main.base.BaseViewEvent
import com.example.profileapplication.app.main.profile.viewmodel.ProfileViewEvent
import com.example.profileapplication.app.main.profile.viewmodel.ProfileViewModel
import com.example.profileapplication.app.main.profile.viewmodel.ProfileViewState
import com.example.profileapplication.navigation.NavigateToKey
import com.example.profileapplication.navigation.Navigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountActivity : BaseActivity<ProfileViewState, ProfileViewModel>() {

    companion object {
        private const val TAG = "AccountActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SetComposeContent()
        }
    }

    private val viewModel: ProfileViewModel by viewModels()

    override val onViewModel: ProfileViewModel
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
    override fun BuildContent(viewState: ProfileViewState) {
        AccountActivityLayout.CONTENT.CreateComposeLayout(
            viewModel = viewModel,
            viewState = viewState,
            navController = null,
            content = null
        )
    }

    override fun onViewEvent(viewEvent: BaseViewEvent) {
        when (viewEvent) {
            is ProfileViewEvent.NavigateBackToLoginActivity -> {
                Navigator.performNavigation(this, null,  NavigateToKey.App.LoginActivity)
                finish()
            }
        }
    }
}