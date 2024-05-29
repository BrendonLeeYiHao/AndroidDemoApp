package com.example.profileapplication.app.main.profile.viewmodel.itemstate

data class ProfileItemValidationState(
    val idError: Boolean = false,
    val nameError: Boolean = false,
    val emailError: Boolean = false,
    val phoneError: Boolean = false,
    val addressError: Boolean = false,
    val idTouched: Boolean = false,
    val nameTouched: Boolean = false,
    val emailTouched: Boolean = false,
    val phoneTouched: Boolean = false,
    val addressTouched: Boolean = false
)