package com.example.profileapplication.app.main.profile.viewmodel.itemstate

import com.example.profileapplication.common.Constants

data class ProfileItemState(
    val id: String = Constants.EMPTY,
    val name: String = Constants.EMPTY,
    val email: String = Constants.EMPTY,
    val phoneNumber: String = Constants.EMPTY,
    val address: String = Constants.EMPTY
)