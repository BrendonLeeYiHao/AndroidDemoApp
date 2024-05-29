package com.example.profileapplication.app.main.profileList.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.profileapplication.app.main.base.BaseViewModel
import com.example.profileapplication.app.main.profile.model.Profile
import com.example.profileapplication.app.main.profile.usecase.ProfileUseCase
import com.example.profileapplication.common.Constants
import com.example.profileapplication.common.ResultWrapper
import com.example.profileapplication.di.datastore.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileListViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase,
    private val dataStoreManager: DataStoreManager
) : BaseViewModel<ProfileListViewState, ProfileListViewEvent>() {

    companion object {
        private const val TAG = "EmailViewModel"
    }

    var profileList: List<Profile>? = listOf()

    init {
        mutableViewState.value = ProfileListViewState()
        viewModelScope.launch {
            val username = dataStoreManager.getUsername()
            val result = profileUseCase.getALlProfile()
            when (result) {
                is ResultWrapper.Success -> {
                    profileList = result.data
                    profileList = profileList?.filter { profile ->
                        profile.name != username
                    }
                    mutableViewState.value = profileList?.let {
                        mutableViewState.value?.copy(
                            list = it
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

    fun deleteProfile(id: String) {
        viewModelScope.launch {
            val result = profileUseCase.deleteProfile(id)
            when (result) {
                is ResultWrapper.Success -> {
                    val message = result.data
                    profileList = profileList?.filter { profile ->
                        profile.id != id
                    }
                    mutableViewState.value = mutableViewState.value?.let {
                        it.copy(
                            list = it.list.filter { profile ->
                                profile.id != id
                            },
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

    fun filterProfile(string: String) {
        val lowerCaseString = string.lowercase()
        mutableViewState.value = mutableViewState.value?.let {
            it.copy(
                list = profileList?.filter { profile ->
                    profile.id.lowercase().contains(lowerCaseString) ||
                    profile.name.lowercase().contains(lowerCaseString) ||
                    profile.email.lowercase().contains(lowerCaseString) ||
                    profile.address.lowercase().contains(lowerCaseString) ||
                    profile.phoneNumber.lowercase().contains(lowerCaseString)
                } ?: emptyList()
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
}