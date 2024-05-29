package com.example.profileapplication.app.main.login.view.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.example.profileapplication.R
import com.example.profileapplication.app.main.base.BaseComposePageLayout
import com.example.profileapplication.app.main.login.viewmodel.LoginViewModel
import com.example.profileapplication.app.main.login.viewmodel.LoginViewState
import com.example.profileapplication.common.Constants
import com.example.profileapplication.ui.component.ShowMessageDialog2

enum class LoginActivityLayout : BaseComposePageLayout<LoginViewState, LoginViewModel> {

    CONTENT {
        @Composable
        override fun CreateComposeLayout(
            viewModel: LoginViewModel,
            viewState: LoginViewState,
            navController: NavController?,
            content: @Composable ((PaddingValues) -> Unit)?
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .background(
                        brush = Brush.verticalGradient(listOf(Color.Red, Color.Magenta))
                    )
                    .fillMaxSize()
                    .padding(
                        top = 80.dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 80.dp
                    )
            ) {
                val (
                    logoBtn,
                    usernameValueTextView,
                    passwordValueTextView,
                    loginBtn,
                    registerTextView,
                    notAMemberYetTextView
                ) = createRefs()

                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = stringResource(id = R.string.logo),
                    modifier = Modifier
                        .constrainAs(logoBtn) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .requiredWidth(240.dp)
                        .requiredHeight(240.dp)
                )

                TextField(
                    value = viewState.username,
                    onValueChange = {
                        viewModel.updateUsername(it)
                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.username))
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = stringResource(id = R.string.username)
                        )
                    },
                    modifier = Modifier
                        .padding(
                            top = 8.dp,
                            bottom = 16.dp
                        )
                        .constrainAs(usernameValueTextView) {
                            top.linkTo(logoBtn.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }
                )

                TextField(
                    value = Constants.EMPTY,
                    onValueChange = {

                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.password))
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = stringResource(id = R.string.password),
                        )
                    },
                    modifier = Modifier
                        .padding(
                            top = 16.dp,
                            bottom = 16.dp
                        )
                        .constrainAs(passwordValueTextView) {
                            top.linkTo(usernameValueTextView.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }
                )

                Button(
                    content = {
                        Text(
                            text = stringResource(id = R.string.login),
                            fontSize = 20.sp)
                    },
                    modifier = Modifier
                        .padding(
                            top = 16.dp,
                            bottom = 8.dp
                        )
                        .constrainAs(loginBtn) {
                            top.linkTo(passwordValueTextView.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        },
                    onClick = {
                        viewModel.login()
                    },
                    shape = MaterialTheme.shapes.medium.copy(all = CornerSize(4.dp))
                )

                Text(
                    modifier = Modifier
                        .padding(
                            top = 8.dp,
                            bottom = 8.dp,
                        )
                        .constrainAs(notAMemberYetTextView) {
                            top.linkTo(loginBtn.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            centerHorizontallyTo(parent)
                            width = Dimension.fillToConstraints
                        },
                    text = stringResource(id = R.string.notAMemberYet),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )

                Text(
                    modifier = Modifier
                        .padding(
                            bottom = 8.dp,
                        )
                        .constrainAs(registerTextView) {
                            top.linkTo(notAMemberYetTextView.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }
                        .clickable {
                            viewModel.navigateToRegistration()
                        },
                    text = stringResource(id = R.string.registerNow),
                    textAlign = TextAlign.Center,
                    textDecoration = TextDecoration.Underline,
                    color = Color.Cyan,
                    fontSize = 16.sp
                )
            }

            if (viewState.showAlertDialog) {
                ShowMessageDialog2(
                    title = viewState.messageTitle,
                    content = viewState.messageBody,
                    onDismissRequest = {
                        viewModel.toggleAlertDialog()
                    }
                )
            }
        }

    }
}