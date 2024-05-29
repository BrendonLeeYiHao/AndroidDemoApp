package com.example.profileapplication.app.main.profile.repository

import com.example.profileapplication.app.main.profile.model.Profile
import com.example.profileapplication.common.ResultWrapper

interface ProfileRemoteRepo {

    suspend fun createProfile(profile: Profile): ResultWrapper<String?>
//    suspend fun getProfile(id: String): Profile
    suspend fun getProfile(id: String): ResultWrapper<Profile?>
    suspend fun updateProfile(profile: Profile): ResultWrapper<String?>
//    suspend fun deleteProfile(id: String): String
    suspend fun deleteProfile(id: String): ResultWrapper<String?>
//    suspend fun getAllProfile(): List<Profile>
    suspend fun getAllProfile(): ResultWrapper<List<Profile>?>
    suspend fun getProfileByName(name: String): ResultWrapper<Profile?>
}