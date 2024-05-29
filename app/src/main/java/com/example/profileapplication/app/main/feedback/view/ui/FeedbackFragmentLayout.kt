package com.example.profileapplication.app.main.feedback.view.ui

import TodoAppScaffold
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.example.profileapplication.R
import com.example.profileapplication.app.main.base.BaseComposePageLayout
import com.example.profileapplication.app.main.feedback.viewmodel.FeedbackViewModel
import com.example.profileapplication.app.main.feedback.viewmodel.FeedbackViewState

enum class FeedbackFragmentLayout : BaseComposePageLayout<FeedbackViewState, FeedbackViewModel>{

    CONTENT {
        @Composable
        override fun CreateComposeLayout(
            viewModel: FeedbackViewModel,
            viewState: FeedbackViewState,
            navController: NavController?,
            content: @Composable ((PaddingValues) -> Unit)?
        ) {
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            val selectedBottomItemIndex = rememberSaveable { mutableIntStateOf(2) }

            TodoAppScaffold(
                title = stringResource(id = R.string.feedback),
                selectedBottomItemIndex = selectedBottomItemIndex,
                navController = navController!!,
                drawerState = drawerState,
                scope = scope,
            ) { it ->
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(
                            top = it.calculateTopPadding(),
                            start = 8.dp,
                            end = 8.dp,
                            bottom = it.calculateBottomPadding()
                        )
                ) {
                    val (
                        idTextView
                    ) = createRefs()

                    Text(
                        modifier = Modifier
                            .padding(
                                top = 8.dp,
                                bottom = 8.dp,
                            )
                            .constrainAs(idTextView) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            },
                        text = stringResource(id = R.string.feedback)
                    )
                }
            }
        }

    }
}