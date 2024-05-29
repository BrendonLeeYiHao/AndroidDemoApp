package com.example.profileapplication.app.main.profile.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.profileapplication.app.main.base.BaseViewModel
import com.example.profileapplication.app.main.profile.model.Profile
import com.example.profileapplication.app.main.profile.usecase.ProfileUseCase
import com.example.profileapplication.app.main.profile.viewmodel.itemstate.ProfileItemState
import com.example.profileapplication.app.main.profile.viewmodel.itemstate.ProfileItemValidationState
import com.example.profileapplication.common.Constants
import com.example.profileapplication.common.ResultWrapper
import com.example.profileapplication.di.datastore.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase,
    private val dataStoreManager: DataStoreManager,
) : BaseViewModel<ProfileViewState, ProfileViewEvent>() {

    companion object {
        private const val TAG = "ProfileViewModel"
    }

    private val formSubmittedMutableLiveData = MutableLiveData(false)
    val formSubmitted: LiveData<Boolean> = formSubmittedMutableLiveData

    init {
        mutableViewState.value = ProfileViewState(
            ProfileItemState(),
            ProfileItemValidationState()
        )
        viewModelScope.launch {
            val username = dataStoreManager.getUsername()
            println("Username Saved is : $username")

            if (username != null) {
                val result = profileUseCase.getProfileByName(username)
                when (result) {
                    is ResultWrapper.Success -> {
                        val profile = result.data!!
                        mutableViewState.value = mutableViewState.value?.let {
                            it.copy(
                                itemState = it.itemState.copy(
                                    id = profile.id,
                                    name = profile.name,
                                    email = profile.email,
                                    phoneNumber = profile.phoneNumber,
                                    address = profile.address
                                )
                            )
                        }
                    }
                    is ResultWrapper.Error -> {
                        val errorMessage = result.message
                        Log.e(TAG, errorMessage)
                    }
                }
            }
        }

    }

    fun updateId(id: String) {
        mutableViewState.value = mutableViewState.value?.let {
            it.copy(
                itemState = it.itemState.copy(
                    id = id
                ),
                itemValidationState = it.itemValidationState.copy(
                    idError = !id.matches(Regex("^P[0-9]{1,4}$")),
                    idTouched = true
                )
            )
        }
    }


    fun updateName(name: String) {
        mutableViewState.value = mutableViewState.value?.let {
            it.copy(
                itemState = it.itemState.copy(
                    name = name
                ),
                itemValidationState = it.itemValidationState.copy(
                    nameError = name.isBlank(),
                    nameTouched = true
                )
            )
        }
    }

    fun updateEmail(email: String) {
        mutableViewState.value = mutableViewState.value?.let {
            it.copy(
                itemState = it.itemState.copy(
                    email = email
                ),
                itemValidationState = it.itemValidationState.copy(
                    emailError = email.isBlank(),
                    emailTouched = true
                )
            )
        }
    }

    fun updatePhone(phone: String) {
        mutableViewState.value = mutableViewState.value?.let {
            it.copy(
                itemState = it.itemState.copy(
                    phoneNumber = phone
                ),
                itemValidationState = it.itemValidationState.copy(
                    phoneError = !phone.matches(Regex("^(01)[0-9]-[0-9]{7}$")),
                    phoneTouched = true
                )
            )
        }
    }

    fun updateAddress(address: String) {
        mutableViewState.value = mutableViewState.value?.let {
            it.copy(
                itemState = it.itemState.copy(
                    address = address
                ),
                itemValidationState = it.itemValidationState.copy(
                    addressError = address.isBlank(),
                    addressTouched = true
                )
            )
        }
    }

    fun toggleAlertDialog() {
        mutableViewState.value = mutableViewState.value?.copy(
            showAlertDialog = !viewState.value!!.showAlertDialog,
            messageTitle = Constants.EMPTY,
            messageBody = Constants.EMPTY
        )
    }

    private fun isAllTouchedFieldsValid(): Boolean {
        return !viewState.value!!.itemValidationState.idError &&
                !viewState.value!!.itemValidationState.nameError &&
                !viewState.value!!.itemValidationState.emailError &&
                !viewState.value!!.itemValidationState.phoneError &&
                !viewState.value!!.itemValidationState.addressError &&
                viewState.value!!.itemValidationState.idTouched &&
                viewState.value!!.itemValidationState.nameTouched &&
                viewState.value!!.itemValidationState.emailTouched &&
                viewState.value!!.itemValidationState.phoneTouched &&
                viewState.value!!.itemValidationState.addressTouched
    }

    private fun isAllFieldsValid(): Boolean {
        return !viewState.value!!.itemValidationState.idError &&
                !viewState.value!!.itemValidationState.nameError &&
                !viewState.value!!.itemValidationState.emailError &&
                !viewState.value!!.itemValidationState.phoneError &&
                !viewState.value!!.itemValidationState.addressError
    }


    fun createProfile() {
        viewModelScope.launch {
            formSubmittedMutableLiveData.postValue(true)

            if (isAllTouchedFieldsValid()) {
                val newProfile = Profile(
                    id = viewState.value!!.itemState.id,
                    name = viewState.value!!.itemState.name,
                    phoneNumber = viewState.value!!.itemState.phoneNumber,
                    email = viewState.value!!.itemState.email,
                    address = viewState.value!!.itemState.address
                )
                val result = profileUseCase.createProfile(newProfile)
                when (result) {
                    is ResultWrapper.Success -> {
                        val message = result.data
                        mutableViewState.value = mutableViewState.value?.let {
                            it.copy(
                                itemState = it.itemState.copy(
                                    id = Constants.EMPTY,
                                    name = Constants.EMPTY,
                                    email = Constants.EMPTY,
                                    phoneNumber = Constants.EMPTY,
                                    address = Constants.EMPTY
                                ),
                                itemValidationState = it.itemValidationState.copy(
                                    idError = false,
                                    nameError = false,
                                    emailError = false,
                                    phoneError = false,
                                    addressError = false,
                                    idTouched = false,
                                    nameTouched = false,
                                    emailTouched = false,
                                    phoneTouched = false,
                                    addressTouched = false
                                ),
                                messageTitle = Constants.SUCCESS,
                                messageBody = message!!,
                                showAlertDialog = true
                            )
                        }
                        formSubmittedMutableLiveData.postValue(false)
                    }

                    is ResultWrapper.Error -> {
                        val errorMessage = result.message
                        Log.e(TAG, errorMessage)
                        mutableViewState.value = mutableViewState.value?.copy(
                            messageTitle = Constants.ERROR,
                            messageBody = errorMessage,
                            showAlertDialog = true
                        )
                    }
                }
            } else {
                println("Check all fields!!!")
            }
        }
    }

    fun getProfile(id: String) {
        viewModelScope.launch {
            val result = profileUseCase.getProfile(id)
            when (result) {
                is ResultWrapper.Success -> {
                    val profile = result.data!!
                    println("PROFILE RETRIEVED: $profile")
                    mutableViewState.value = mutableViewState.value?.let {
                        it.copy(
                            itemState = it.itemState.copy(
                                id = profile.id,
                                name = profile.name,
                                email = profile.email,
                                phoneNumber = profile.phoneNumber,
                                address = profile.address
                            )
                        )
                    }
                }

                is ResultWrapper.Error -> {
                    val errorMessage = result.message
                    Log.e(TAG, errorMessage)
                    mutableViewState.value = mutableViewState.value?.copy(
                        messageTitle = Constants.ERROR,
                        messageBody = errorMessage,
                        showAlertDialog = true
                    )
                }
            }
        }
    }

    fun updateProfile() {
        viewModelScope.launch {
            if (isAllFieldsValid()) {
                val updatedProfile = Profile(
                    id = viewState.value!!.itemState.id,
                    name = viewState.value!!.itemState.name,
                    email = viewState.value!!.itemState.email,
                    phoneNumber = viewState.value!!.itemState.phoneNumber,
                    address = viewState.value!!.itemState.address
                )
                val result = profileUseCase.updateProfile(updatedProfile)
                when (result) {
                    is ResultWrapper.Success -> {
                        val message = result.data
                        mutableViewState.value = mutableViewState.value?.let {
                            it.copy(
                                itemState = it.itemState.copy(
                                    id = updatedProfile.id,
                                    name = updatedProfile.name,
                                    email = updatedProfile.email,
                                    phoneNumber = updatedProfile.phoneNumber,
                                    address = updatedProfile.address
                                ),
                                messageTitle = Constants.SUCCESS,
                                messageBody = message!!,
                                showAlertDialog = true
                            )
                        }
                    }

                    is ResultWrapper.Error -> {
                        val errorMessage = result.message
                        Log.e(TAG, errorMessage)
                        mutableViewState.value = mutableViewState.value?.copy(
                            messageTitle = Constants.ERROR,
                            messageBody = errorMessage,
                            showAlertDialog = true
                        )
                    }
                }
            }
        }
    }

    fun deleteProfile() {
        viewModelScope.launch {
            val result = profileUseCase.deleteProfile(viewState.value!!.itemState.id)
            when (result) {
                is ResultWrapper.Success -> {
                    val message = result.data
                    mutableViewState.value = mutableViewState.value?.let {
                        it.copy(
                            itemState = it.itemState.copy(
                                id = Constants.EMPTY,
                                name = Constants.EMPTY,
                                email = Constants.EMPTY,
                                phoneNumber = Constants.EMPTY,
                                address = Constants.EMPTY
                            ),
                            itemValidationState = it.itemValidationState.copy(
                                idError = false,
                                nameError = false,
                                emailError = false,
                                phoneError = false,
                                addressError = false,
                                idTouched = false,
                                nameTouched = false,
                                emailTouched = false,
                                phoneTouched = false,
                                addressTouched = false
                            ),
                            messageTitle = Constants.SUCCESS,
                            messageBody = message!!,
                            showAlertDialog = true
                        )
                    }
                }

                is ResultWrapper.Error -> {
                    val errorMessage = result.message
                    Log.e(TAG, errorMessage)
                    mutableViewState.value = mutableViewState.value?.copy(
                        messageTitle = Constants.ERROR,
                        messageBody = errorMessage,
                        showAlertDialog = true
                    )
                }
            }
        }
    }

    fun navigateToProfileFragment() {
        navigate(ProfileViewEvent.NavigateToFeedbackFragment)
    }

    fun navigateBack() {
        navigate(ProfileViewEvent.NavigateBackToLoginActivity)
    }
}