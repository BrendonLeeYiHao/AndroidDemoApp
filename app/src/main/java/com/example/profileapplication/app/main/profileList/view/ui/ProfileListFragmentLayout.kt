package com.example.profileapplication.app.main.profileList.view.ui

import TodoAppScaffold
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.example.profileapplication.R
import com.example.profileapplication.app.main.base.BaseComposePageLayout
import com.example.profileapplication.app.main.profileList.viewmodel.ProfileListViewModel
import com.example.profileapplication.app.main.profileList.viewmodel.ProfileListViewState
import com.example.profileapplication.common.Constants
import com.example.profileapplication.ui.component.ShowMessageDialog2

enum class ProfileListFragmentLayout : BaseComposePageLayout<ProfileListViewState, ProfileListViewModel> {

    CONTENT {
        @Composable
        override fun CreateComposeLayout(
            viewModel: ProfileListViewModel,
            viewState: ProfileListViewState,
            navController: NavController?,
            content: @Composable ((PaddingValues) -> Unit)?
        ) {
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            val selectedBottomItemIndex = rememberSaveable { mutableIntStateOf(1) }

            TodoAppScaffold(
                title = stringResource(id = R.string.profileList),
                selectedBottomItemIndex = selectedBottomItemIndex,
                navController = navController,
                drawerState = drawerState,
                scope = scope
            ) { it ->
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(
                            top = it.calculateTopPadding(),
                            start = 16.dp,
                            end = 16.dp,
                            bottom = it.calculateBottomPadding()
                        )
                ) {
                    val (
                        searchValueTextView,
                        profileListColumn
                    ) = createRefs()
                    val search = remember { mutableStateOf(Constants.EMPTY) }

                    TextField(
                        value = search.value,
                        onValueChange = {
                            search.value = it
                            viewModel.filterProfile(it)
                        },
                        placeholder = {
                            Text(text = stringResource(id = R.string.typeToSearch))
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        },
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .constrainAs(searchValueTextView) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                        },
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .constrainAs(profileListColumn) {
                                top.linkTo(searchValueTextView.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            }
                    ) {
                        viewState.list.forEachIndexed { _, profile ->
                            println("Profile Received are: " + profile.name)
                            ElevatedCard(
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 6.dp
                                ),
                                modifier = Modifier
                                    .padding(vertical = 8.dp)
                                    .fillMaxWidth()
                            ) {
                                ConstraintLayout(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            top = 8.dp,
                                            start = 16.dp,
                                            end = 16.dp,
                                            bottom = 8.dp
                                        )
                                ) {
                                    val (
                                        nameTextView,
                                        idTextView,
                                        phoneIcon,
                                        phoneTextView,
                                        emailIcon,
                                        emailTextView,
                                        addressIcon,
                                        addressTextView,
                                        editIcon,
                                        deleteIcon
                                    ) = createRefs()

                                    Text(
                                        text = profile.name,
                                        fontSize = 24.sp,
                                        modifier = Modifier
                                            .padding(top = 8.dp, bottom = 8.dp)
                                            .constrainAs(nameTextView) {
                                                top.linkTo(parent.top)
                                                start.linkTo(parent.start)
                                                end.linkTo(parent.end)
                                                width = Dimension.fillToConstraints
                                            },
                                    )
                                    Text(
                                        text = profile.id,
                                        modifier = Modifier
                                            .padding(top = 8.dp, bottom = 8.dp)
                                            .constrainAs(idTextView) {
                                                top.linkTo(nameTextView.bottom)
                                                start.linkTo(parent.start)
                                                end.linkTo(parent.end)
                                                width = Dimension.fillToConstraints
                                            },
                                    )
                                    Icon(
                                        imageVector = Icons.Default.Phone,
                                        contentDescription = stringResource(id = R.string.phone),
                                        modifier = Modifier
                                            .padding(top = 8.dp, bottom = 8.dp)
                                            .constrainAs(phoneIcon) {
                                                top.linkTo(idTextView.bottom)
                                                start.linkTo(parent.start)
                                                width = Dimension.wrapContent
                                                height = Dimension.wrapContent
                                            }
                                    )

                                    Text(
                                        text = profile.phoneNumber,
                                        modifier = Modifier
                                            .padding(top = 8.dp, bottom = 8.dp)
                                            .constrainAs(phoneTextView) {
                                                top.linkTo(phoneIcon.top)
                                                bottom.linkTo(phoneIcon.bottom)
                                                start.linkTo(phoneIcon.end, margin = 8.dp)
                                                end.linkTo(parent.end)
                                                width = Dimension.fillToConstraints
                                            }

                                    )

                                    ElevatedButton(
                                        onClick = {
                                            // To be Implemented
                                        },
                                        modifier = Modifier
                                            .size(54.dp)
                                            .constrainAs(editIcon) {
                                                top.linkTo(idTextView.top)
                                                bottom.linkTo(idTextView.bottom)
                                                end.linkTo(deleteIcon.start, margin = 16.dp)
                                            },
                                        shape = CircleShape,
                                        contentPadding = PaddingValues(0.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = stringResource(id = R.string.edit),
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }

                                    ElevatedButton(
                                        onClick = {
                                            viewModel.deleteProfile(profile.id)
                                        },
                                        modifier = Modifier
                                            .size(54.dp)
                                            .constrainAs(deleteIcon) {
                                                top.linkTo(idTextView.top)
                                                bottom.linkTo(idTextView.bottom)
                                                end.linkTo(parent.end)
                                            },
                                        shape = CircleShape,
                                        contentPadding = PaddingValues(0.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = stringResource(id = R.string.delete),
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }

                                    Icon(
                                        imageVector = Icons.Default.Email,
                                        contentDescription = stringResource(id = R.string.email),
                                        modifier = Modifier
                                            .padding(top = 8.dp, bottom = 8.dp)
                                            .constrainAs(emailIcon) {
                                                top.linkTo(phoneIcon.bottom)
                                                start.linkTo(parent.start)
                                                width = Dimension.wrapContent
                                                height = Dimension.wrapContent
                                            }
                                    )
                                    Text(
                                        text = profile.email,
                                        modifier = Modifier
                                            .padding(top = 8.dp, bottom = 8.dp)
                                            .constrainAs(emailTextView) {
                                                top.linkTo(emailIcon.top)
                                                bottom.linkTo(emailIcon.bottom)
                                                start.linkTo(emailIcon.end, margin = 8.dp)
                                                end.linkTo(parent.end)
                                                width = Dimension.fillToConstraints
                                            }
                                    )

//                                        ElevatedButton(
//                                            onClick = {
//                                                viewModel.deleteProfile(profile.id)
//                                            },
//                                            modifier = Modifier
//                                                .size(54.dp)
//                                                .constrainAs(deleteIcon) {
//                                                    top.linkTo(emailTextView.top)
//                                                    bottom.linkTo(emailTextView.bottom)
//                                                    end.linkTo(parent.end)
//                                                },
//                                            shape = CircleShape,
//                                            contentPadding = PaddingValues(0.dp)
//                                        ) {
//                                            Icon(
//                                                imageVector = Icons.Default.Delete,
//                                                contentDescription = stringResource(id = R.string.delete),
//                                                modifier = Modifier.size(24.dp)
//                                            )
//                                        }

                                    Icon(
                                        imageVector = Icons.Default.LocationOn,
                                        contentDescription = stringResource(id = R.string.address),
                                        modifier = Modifier
                                            .padding(top = 8.dp, bottom = 8.dp)
                                            .constrainAs(addressIcon) {
                                                top.linkTo(emailIcon.bottom)
                                                start.linkTo(parent.start)
                                                width = Dimension.wrapContent
                                                height = Dimension.wrapContent
                                            }
                                    )
                                    Text(
                                        text = profile.address,
                                        modifier = Modifier
                                            .padding(top = 8.dp, bottom = 8.dp)
                                            .constrainAs(addressTextView) {
                                                top.linkTo(addressIcon.top)
                                                bottom.linkTo(addressIcon.bottom)
                                                start.linkTo(addressIcon.end, margin = 8.dp)
                                                end.linkTo(parent.end)
                                                width = Dimension.fillToConstraints
                                            }
                                    )
                                }
                            }
                        }
                    }
                }
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