package com.example.profileapplication.app.main.profile.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.profileapplication.app.main.base.BaseFragment
import com.example.profileapplication.app.main.base.BaseViewEvent
import com.example.profileapplication.app.main.profile.view.ui.ProfileFragmentLayoutReuse
import com.example.profileapplication.app.main.profile.viewmodel.ProfileViewEvent
import com.example.profileapplication.app.main.profile.viewmodel.ProfileViewModel
import com.example.profileapplication.app.main.profile.viewmodel.ProfileViewState
import com.example.profileapplication.navigation.NavigateToKey
import com.example.profileapplication.navigation.Navigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<ProfileViewState, ProfileViewModel>() {

    private lateinit var navController: NavController


    companion object {
        private const val TAG = "ProfileActivity"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                SetComposeContent()
            }
        }
    }

    private val viewModel: ProfileViewModel by viewModels()

    override val onViewModel: ProfileViewModel
        get() = viewModel

    @Composable
    override fun SetComposeContent() {
        navController = findNavController()
        viewModel.viewState.observeAsState().value?.let { nonNullViewState ->
            BuildContent(viewState = nonNullViewState, navController = navController)
        } ?: run {
            Log.e(TAG, "ViewState cannot be null!")
        }

        viewModel.viewEvent.observeAsState().value?.let {
            onViewEvent(it)
        }
    }

    @Composable
    override fun BuildContent(viewState: ProfileViewState, navController: NavController?) {
        ProfileFragmentLayoutReuse.CONTENT.CreateComposeLayout(
            viewModel = viewModel,
            viewState = viewState,
            navController = navController,
            content = null
        )
    }

    override fun onViewEvent(viewEvent: BaseViewEvent) {
        when (viewEvent) {
            is ProfileViewEvent.NavigateToFeedbackFragment -> {
                Navigator.performNavigation(null, navController, NavigateToKey.App.FeedbackFragment)
            }
            is ProfileViewEvent.NavigateToOthers -> {
                // TODO - To be Impl
            }
        }
    }
}