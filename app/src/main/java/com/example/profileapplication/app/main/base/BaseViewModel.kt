package com.example.profileapplication.app.main.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<VIEW_STATE : BaseViewState, VIEW_EVENT : BaseViewEvent> : ViewModel() {

    protected val mutableViewState: MutableLiveData<VIEW_STATE> = MutableLiveData()
    protected val mutableViewEvent: MutableLiveData<VIEW_EVENT> = MutableLiveData()
    protected val isLoadingMutableLiveData = MutableLiveData<Boolean>()

    val viewState: LiveData<VIEW_STATE> = mutableViewState
    val viewEvent: LiveData<VIEW_EVENT> = mutableViewEvent
    val isLoading: LiveData<Boolean> = isLoadingMutableLiveData

    fun navigate(event: VIEW_EVENT) {
        mutableViewEvent.value = event
    }

}