package com.example.profileapplication.network

import com.example.profileapplication.app.main.profile.model.Profile
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @GET("/profile/get-details/{id}")
    suspend fun getProfile(@Path("id") id: String): Response<Profile>

    @POST("/profile")
    suspend fun createProfile(@Body profile: Profile): Response<String>

    @PUT("/profile")
    suspend fun updateProfile(@Body profile: Profile): Response<String>

    @DELETE("/profile/delete-profile/{id}")
    suspend fun deleteProfile(@Path("id") id: String): Response<String>

    @GET("/profile/get-details")
    suspend fun getAllProfile(): Response<List<Profile>>

    @GET("/profile/login/{name}")
    suspend fun login(@Path("name") name: String): Response<String>

    @GET("/profile/get-detail/{name}")
    suspend fun getProfileByName(@Path("name") name: String): Response<Profile>
}