package com.example.profileapplication.app.main.account.view.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.example.profileapplication.R
import com.example.profileapplication.app.main.base.BaseComposePageLayout
import com.example.profileapplication.app.main.profile.viewmodel.ProfileViewModel
import com.example.profileapplication.app.main.profile.viewmodel.ProfileViewState
import com.example.profileapplication.common.Constants
import com.example.profileapplication.ui.component.ShowMessageDialog2

enum class AccountActivityLayout : BaseComposePageLayout<ProfileViewState, ProfileViewModel> {

    CONTENT {
        @OptIn(ExperimentalMaterial3Api::class)
        @Composable
        override fun CreateComposeLayout(
            viewModel: ProfileViewModel,
            viewState: ProfileViewState,
            navController: NavController?,
            content: @Composable ((PaddingValues) -> Unit)?
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(text = stringResource(id = R.string.registration)) },
                        navigationIcon = {
                            IconButton(onClick = {
                                viewModel.navigateBack()
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = stringResource(id = R.string.back)
                                )
                            }
                        }
                    )
                }
            ) {
                it ->
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
                        idTextView,
                        idValueTextView,
                        nameTextView,
                        nameValueTextView,
                        phoneTextView,
                        phoneValueTextView,
                        emailTextView,
                        emailValueTextView,
                        addressTextView,
                        addressValueTextView
                    ) = createRefs()

                    val (
                        submitButton,
                    ) = createRefs()

                    val (
                        idErrorTextView,
                        nameErrorTextView,
                        emailErrorTextView,
                        phoneErrorTextView,
                        addressErrorTextView
                    ) = createRefs()

                    val clickSubmitButton by viewModel.formSubmitted.observeAsState()

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
                        text = stringResource(id = R.string.id)
                    )

                    TextField(
                        value = viewState.itemState.id,
                        onValueChange = {
                            viewModel.updateId(it)
                        },
                        isError = viewState.itemValidationState.idError,
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = stringResource(id = R.string.info)
                            )
                        },
                        modifier = Modifier.constrainAs(idValueTextView) {
                            top.linkTo(idTextView.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }
                    )

                    Text(
                        modifier = Modifier
                            .padding(
                                top = 4.dp
                            )
                            .constrainAs(idErrorTextView) {
                                top.linkTo(idValueTextView.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            },
                        text = if (viewState.itemValidationState.idError || (!viewState.itemValidationState.idTouched && clickSubmitButton!!))
                            stringResource(id = R.string.idErrorMsg) else Constants.EMPTY,
                        color = Color.Red
                    )

                    Text(
                        modifier = Modifier
                            .padding(
                                top = 8.dp,
                                bottom = 8.dp,
                            )
                            .constrainAs(nameTextView) {
                                top.linkTo(idErrorTextView.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            },
                        text = stringResource(id = R.string.name)
                    )

                    TextField(
                        value = viewState.itemState.name,
                        onValueChange = {
                            viewModel.updateName(it)
                        },
                        isError = viewState.itemValidationState.nameError,
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = stringResource(id = R.string.info)
                            )
                        },
                        modifier = Modifier.constrainAs(nameValueTextView) {
                            top.linkTo(nameTextView.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }
                    )

                    Text(
                        modifier = Modifier
                            .padding(
                                top = 4.dp
                            )
                            .constrainAs(nameErrorTextView) {
                                top.linkTo(nameValueTextView.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            },
                        text = if (viewState.itemValidationState.nameError || (!viewState.itemValidationState.nameTouched && clickSubmitButton!!))
                            stringResource(id = R.string.nonEmptyFieldMsg) else Constants.EMPTY,
                        color = Color.Red
                    )

                    Text(
                        modifier = Modifier
                            .padding(
                                top = 8.dp,
                                bottom = 8.dp
                            )
                            .constrainAs(phoneTextView) {
                                top.linkTo(nameErrorTextView.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            },
                        text = stringResource(id = R.string.phone)
                    )

                    TextField(
                        value = viewState.itemState.phoneNumber,
                        onValueChange = {
                            viewModel.updatePhone(it)
                        },
                        isError = viewState.itemValidationState.phoneError,
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = stringResource(id = R.string.info)
                            )
                        },
                        modifier = Modifier.constrainAs(phoneValueTextView) {
                            top.linkTo(phoneTextView.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }
                    )

                    Text(
                        modifier = Modifier
                            .padding(
                                top = 4.dp
                            )
                            .constrainAs(phoneErrorTextView) {
                                top.linkTo(phoneValueTextView.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            },
                        text = if (viewState.itemValidationState.phoneError || (!viewState.itemValidationState.phoneTouched && clickSubmitButton!!))
                            stringResource(id = R.string.phoneErrorMsg) else Constants.EMPTY,
                        color = Color.Red
                    )

                    Text(
                        modifier = Modifier
                            .padding(
                                top = 8.dp,
                                bottom = 8.dp
                            )
                            .constrainAs(emailTextView) {
                                top.linkTo(phoneErrorTextView.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            },
                        text = stringResource(id = R.string.email)
                    )

                    TextField(
                        value = viewState.itemState.email,
                        onValueChange = {
                            viewModel.updateEmail(it)
                        },
                        isError = viewState.itemValidationState.emailError,
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = stringResource(id = R.string.info)
                            )
                        },
                        modifier = Modifier.constrainAs(emailValueTextView) {
                            top.linkTo(emailTextView.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }
                    )

                    Text(
                        modifier = Modifier
                            .padding(
                                top = 4.dp
                            )
                            .constrainAs(emailErrorTextView) {
                                top.linkTo(emailValueTextView.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            },
                        text = if (viewState.itemValidationState.emailError || (!viewState.itemValidationState.emailTouched && clickSubmitButton!!))
                            stringResource(id = R.string.nonEmptyFieldMsg) else Constants.EMPTY,
                        color = Color.Red
                    )

                    Text(
                        modifier = Modifier
                            .padding(
                                top = 8.dp,
                                bottom = 8.dp
                            )
                            .constrainAs(addressTextView) {
                                top.linkTo(emailErrorTextView.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            },
                        text = stringResource(id = R.string.address)
                    )

                    TextField(
                        value = viewState.itemState.address,
                        onValueChange = {
                            viewModel.updateAddress(it)
                        },
                        isError = viewState.itemValidationState.addressError,
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = stringResource(id = R.string.info)
                            )
                        },
                        modifier = Modifier.constrainAs(addressValueTextView) {
                            top.linkTo(addressTextView.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }
                    )

                    Text(
                        modifier = Modifier
                            .padding(
                                top = 4.dp
                            )
                            .constrainAs(addressErrorTextView) {
                                top.linkTo(addressValueTextView.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            },
                        text = if (viewState.itemValidationState.addressError || (!viewState.itemValidationState.addressTouched && clickSubmitButton!!))
                            stringResource(id = R.string.nonEmptyFieldMsg) else Constants.EMPTY,
                        color = Color.Red
                    )

                    Button(
                        onClick = { viewModel.createProfile() },
                        modifier = Modifier
                            .padding(
                                top = 8.dp,
                                bottom = 8.dp
                            )
                            .constrainAs(submitButton) {
                                top.linkTo(addressErrorTextView.bottom)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            }
                    ) {
                        Text(text = stringResource(id = R.string.create))
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

    }
}