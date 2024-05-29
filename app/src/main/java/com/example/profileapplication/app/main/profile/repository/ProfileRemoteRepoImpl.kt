package com.example.profileapplication.app.main.profile.repository

import com.example.profileapplication.app.main.profile.model.Profile
import com.example.profileapplication.common.ResultWrapper
import com.example.profileapplication.common.handleApiResponse
import com.example.profileapplication.network.ApiService
import javax.inject.Inject

class ProfileRemoteRepoImpl @Inject constructor(
    private val apiService: ApiService
) : ProfileRemoteRepo {

    override suspend fun createProfile(profile: Profile): ResultWrapper<String?> {
        return handleApiResponse { apiService.createProfile(profile) }
    }

    override suspend fun getProfile(id: String): ResultWrapper<Profile?> {
        return handleApiResponse { apiService.getProfile(id) }
    }

    override suspend fun updateProfile(profile: Profile): ResultWrapper<String?> {
        return handleApiResponse { apiService.updateProfile(profile) }
    }

    override suspend fun deleteProfile(id: String): ResultWrapper<String?> {
        return handleApiResponse { apiService.deleteProfile(id) }
    }

    override suspend fun getAllProfile(): ResultWrapper<List<Profile>?> {
        return handleApiResponse { apiService.getAllProfile() }
    }

    override suspend fun getProfileByName(name: String): ResultWrapper<Profile?> {
        return handleApiResponse { apiService.getProfileByName(name) }
    }
}