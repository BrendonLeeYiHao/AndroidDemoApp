package com.example.profileapplication.app.main.profileList.viewmodel

import com.example.profileapplication.app.main.base.BaseViewState
import com.example.profileapplication.app.main.profile.model.Profile
import com.example.profileapplication.common.Constants

data class ProfileListViewState(
    val isLoading: Boolean = false,
    val list: List<Profile> = emptyList(),
    val messageTitle: String = Constants.EMPTY,
    val messageBody: String = Constants.EMPTY,
    val showAlertDialog: Boolean = false
) : BaseViewState
