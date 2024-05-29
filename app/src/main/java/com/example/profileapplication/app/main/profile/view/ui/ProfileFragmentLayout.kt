package com.example.profileapplication.app.main.profile.view.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.example.profileapplication.navigation.BottomNavigationItem
import com.example.profileapplication.navigation.NavigationItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Not In Use
enum class ProfileFragmentLayout: BaseComposePageLayout<ProfileViewState, ProfileViewModel> {

    CONTENT {
        @OptIn(ExperimentalMaterial3Api::class)
        @Composable
        override fun CreateComposeLayout(
            viewModel: ProfileViewModel,
            viewState: ProfileViewState,
            navController: NavController?,
            content: @Composable ((PaddingValues) -> Unit)?
        ) {
            val items = enumValues<NavigationItem>().toList()

            val bottomItems = enumValues<BottomNavigationItem>().toList()

            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            var selectedItemIndex by rememberSaveable {
                mutableIntStateOf(0)
            }
            var selectedBottomItemIndex by rememberSaveable {
                mutableIntStateOf(0)
            }
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Todo App", modifier = Modifier.padding(16.dp))
                        Divider(modifier = Modifier.padding(
                            bottom = 8.dp
                        ))
                        items.forEachIndexed { index, item ->
                            NavigationDrawerItem(
                                label = { Text(text = stringResource(id = item.title)) },
                                selected = index == selectedItemIndex,
                                onClick = {
                                    selectedItemIndex = index
                                    scope.launch {
                                        drawerState.close()
                                    }
                                },
                                icon = {
                                    Icon(
                                        imageVector = if (index == selectedItemIndex) {
                                            item.selectedIcon
                                        } else item.unselectedIcon,
                                        contentDescription = stringResource(id = item.title)
                                    )
                                },
                                modifier = Modifier.padding(
                                    NavigationDrawerItemDefaults.ItemPadding
                                )
                            )
                        }
                    }
                }
            ) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = "Registration")
                            },
                            navigationIcon = {
                                IconButton(onClick = {
                                    scope.launch {
                                        drawerState.open()
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Menu,
                                        contentDescription = "Menu"
                                    )
                                }
                            }
                        )
                    },
                    bottomBar = {
                        NavigationBar {
                            bottomItems.forEachIndexed { index, bottomNavigationItem ->
                                NavigationBarItem(
                                    label = {
                                        Text(text = stringResource(id = bottomNavigationItem.title))
                                    },
                                    selected = selectedBottomItemIndex == index,
                                    onClick = {
                                        selectedBottomItemIndex = index
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = if (index == selectedBottomItemIndex) {
                                                bottomNavigationItem.selectedIcon
                                            } else bottomNavigationItem.unselectedIcon,
                                            contentDescription = stringResource(id = bottomNavigationItem.title)
                                        )
                                    })
                            }
                        }
                    },
                    content = { it ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
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
                                    searchValueTextView,
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
                                    updateButton,
                                    deleteButton
                                ) = createRefs()

                                val (
                                    idErrorTextView,
                                    nameErrorTextView,
                                    emailErrorTextView,
                                    phoneErrorTextView,
                                    addressErrorTextView
                                ) = createRefs()

                                val clickSubmitButton by viewModel.formSubmitted.observeAsState()

                                val search = remember { mutableStateOf(Constants.EMPTY) }

                                TextField(
                                    value = search.value,
                                    onValueChange = {
                                        search.value = it
                                    },
                                    placeholder = {
                                        Text(text = stringResource(id = R.string.typeToSearch))
                                    },
                                    trailingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.Search,
                                            contentDescription = "Search",
                                            modifier = Modifier.clickable {
                                                viewModel.getProfile(search.value)
                                            }
                                        )
                                    },
                                    modifier = Modifier.constrainAs(searchValueTextView) {
                                        top.linkTo(parent.top)
                                        start.linkTo(parent.start)
                                        end.linkTo(parent.end)
                                        width = Dimension.fillToConstraints
                                    },
                                )

                                Text(
                                    modifier = Modifier
                                        .padding(
                                            top = 8.dp,
                                            bottom = 8.dp,
                                        )
                                        .constrainAs(idTextView) {
                                            top.linkTo(searchValueTextView.bottom)
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
                                            contentDescription = "Info"
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
                                    text = if (viewState.itemValidationState.idError || (!viewState.itemValidationState.idTouched && clickSubmitButton!!)) "Only P1 - P9999" else Constants.EMPTY,
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
                                            contentDescription = "Info"
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
                                    text = if (viewState.itemValidationState.nameError || (!viewState.itemValidationState.nameTouched && clickSubmitButton!!)) "Field must not be empty" else Constants.EMPTY,
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
                                            contentDescription = "Info"
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
                                    text = if (viewState.itemValidationState.phoneError || (!viewState.itemValidationState.phoneTouched && clickSubmitButton!!)) "Phone Format: 01X-XXXXXXX, where X can be 0 - 9" else Constants.EMPTY,
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
                                            contentDescription = "Info"
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
                                    text = if (viewState.itemValidationState.emailError || (!viewState.itemValidationState.emailTouched && clickSubmitButton!!)) "Field must not be empty" else Constants.EMPTY,
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
                                    value = Constants.EMPTY,
                                    onValueChange = {},
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
                                    text = if (viewState.itemValidationState.addressError || (!viewState.itemValidationState.addressTouched && clickSubmitButton!!)) "Field must not be empty" else Constants.EMPTY,
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
                                            end.linkTo(updateButton.start)
                                            width = Dimension.fillToConstraints
                                        }
                                ) {
                                    Text(text = stringResource(id = R.string.create))
                                }

                                Button(
                                    onClick = { viewModel.navigateToProfileFragment() },
                                    modifier = Modifier
                                        .padding(
                                            top = 8.dp,
                                            bottom = 8.dp
                                        )
                                        .constrainAs(updateButton) {
                                            top.linkTo(addressErrorTextView.bottom)
                                            end.linkTo(deleteButton.start)
                                            width = Dimension.fillToConstraints
                                        }
                                ) {
                                    Text(text = stringResource(id = R.string.update))
                                }

                                Button(
                                    onClick = { viewModel.deleteProfile() },
                                    modifier = Modifier
                                        .padding(
                                            top = 8.dp,
                                            bottom = 8.dp
                                        )
                                        .constrainAs(deleteButton) {
                                            top.linkTo(addressErrorTextView.bottom)
                                            end.linkTo(parent.end)
                                            width = Dimension.fillToConstraints
                                        }
                                ) {
                                    Text(text = stringResource(id = R.string.delete))
                                }

                                if (viewState.showAlertDialog) {
                                    ShowMessageDialog(
                                        title = viewState.messageTitle,
                                        content = viewState.messageBody,
                                        onDismissRequest = {
                                            viewModel.toggleAlertDialog()
                                        }
                                    )
                                }
                            }

                            val isLoading = remember { mutableStateOf(true) }

                            if (isLoading.value) {
                                CircularProgressIndicator(
                                    modifier = Modifier.align(Alignment.Center),
                                    color = Color.Magenta,
                                    strokeWidth = 4.dp
                                )
                                LaunchedEffect(Unit) {
                                    delay(5000)
                                    isLoading.value = false
                                }
                            }
                        }
                    }
                )
            }
        }

    }
}

@Composable
fun ShowMessageDialog(
    title: String,
    content: String,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(title) },
        text = { Text(content) },
        confirmButton = {
            Button(onClick = onDismissRequest) {
                Text(stringResource(id = R.string.okay))
            }
        }
    )

}
