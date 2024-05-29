package com.example.profileapplication.app.main.profile.usecase

import com.example.profileapplication.app.main.profile.model.Profile
import com.example.profileapplication.app.main.profile.repository.ProfileRemoteRepo
import com.example.profileapplication.common.ResultWrapper
import javax.inject.Inject

class ProfileUseCaseImpl @Inject constructor(
    private val profileRemoteRepo: ProfileRemoteRepo
) : ProfileUseCase {

    override suspend fun createProfile(profile: Profile): ResultWrapper<String?> {
        return profileRemoteRepo.createProfile(profile)
    }

//    override suspend fun getProfile(id: String): Profile {
//        return profileRemoteRepo.getProfile(id)
//    }

    override suspend fun getProfile(id: String): ResultWrapper<Profile?> {
        return profileRemoteRepo.getProfile(id)
    }

    override suspend fun updateProfile(profile: Profile): ResultWrapper<String?> {
        return profileRemoteRepo.updateProfile(profile)
    }

//    override suspend fun deleteProfile(id: String): String {
//        return profileRemoteRepo.deleteProfile(id)
//    }

    override suspend fun deleteProfile(id: String): ResultWrapper<String?> {
        return profileRemoteRepo.deleteProfile(id)
    }

//    override suspend fun getAllProfile(): List<Profile> {
//        return profileRemoteRepo.getAllProfile()
//    }

    override suspend fun getALlProfile(): ResultWrapper<List<Profile>?> {
        return profileRemoteRepo.getAllProfile()
    }

    override suspend fun getProfileByName(name: String): ResultWrapper<Profile?> {
        return profileRemoteRepo.getProfileByName(name)
    }
}