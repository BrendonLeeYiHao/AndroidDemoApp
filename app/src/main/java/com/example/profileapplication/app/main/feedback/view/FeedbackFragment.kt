package com.example.profileapplication.app.main.feedback.view

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
import com.example.profileapplication.app.main.feedback.view.ui.FeedbackFragmentLayout
import com.example.profileapplication.app.main.feedback.viewmodel.FeedbackViewModel
import com.example.profileapplication.app.main.feedback.viewmodel.FeedbackViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedbackFragment : BaseFragment<FeedbackViewState, FeedbackViewModel>() {

    private lateinit var navController: NavController

    companion object {
        private const val TAG = "FeedbackFragment"
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

    private val viewModel: FeedbackViewModel by viewModels()

    override val onViewModel: FeedbackViewModel
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
    override fun BuildContent(viewState: FeedbackViewState, navController: NavController?) {
        FeedbackFragmentLayout.CONTENT.CreateComposeLayout(
            viewModel = viewModel,
            viewState = viewState,
            navController = navController,
            content = null
        )
    }

    override fun onViewEvent(viewEvent: BaseViewEvent) {
        TODO("Not yet implemented")
    }
}