package com.example.profileapplication.app.main.profile.viewmodel

import com.example.profileapplication.app.main.base.BaseViewState
import com.example.profileapplication.app.main.profile.viewmodel.itemstate.ProfileItemState
import com.example.profileapplication.app.main.profile.viewmodel.itemstate.ProfileItemValidationState
import com.example.profileapplication.common.Constants

data class ProfileViewState(
    val itemState: ProfileItemState,
    val itemValidationState: ProfileItemValidationState,
    val messageTitle: String = Constants.EMPTY,
    val messageBody: String = Constants.EMPTY,
    val showAlertDialog: Boolean = false
): BaseViewState
