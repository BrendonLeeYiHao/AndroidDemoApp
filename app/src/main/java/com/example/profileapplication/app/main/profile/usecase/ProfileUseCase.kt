package com.example.profileapplication.app.main.profile.usecase

import com.example.profileapplication.app.main.profile.model.Profile
import com.example.profileapplication.common.ResultWrapper

interface ProfileUseCase {

    suspend fun createProfile(profile: Profile): ResultWrapper<String?>
    suspend fun getProfile(id: String): ResultWrapper<Profile?>
    suspend fun updateProfile(profile: Profile): ResultWrapper<String?>
    suspend fun deleteProfile(id: String): ResultWrapper<String?>
    suspend fun getALlProfile(): ResultWrapper<List<Profile>?>
    suspend fun getProfileByName(name: String): ResultWrapper<Profile?>
}