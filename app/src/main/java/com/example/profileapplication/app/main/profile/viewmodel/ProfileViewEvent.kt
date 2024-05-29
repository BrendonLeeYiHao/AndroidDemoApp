package com.example.profileapplication.app.main.profile.viewmodel

import com.example.profileapplication.app.main.base.BaseViewEvent

sealed class ProfileViewEvent: BaseViewEvent {

    data object NavigateToFeedbackFragment: ProfileViewEvent()
    data object NavigateToOthers: ProfileViewEvent()
    data object NavigateBackToLoginActivity: ProfileViewEvent()
}
