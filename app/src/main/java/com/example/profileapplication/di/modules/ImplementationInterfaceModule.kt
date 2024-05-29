package com.example.profileapplication.di.modules

import com.example.profileapplication.app.main.feedback.repository.FeedbackRemoteRepo
import com.example.profileapplication.app.main.feedback.repository.FeedbackRemoteRepoImpl
import com.example.profileapplication.app.main.feedback.usecase.FeedbackUseCase
import com.example.profileapplication.app.main.feedback.usecase.FeedbackUseCaseImpl
import com.example.profileapplication.app.main.login.repository.LoginRemoteRepo
import com.example.profileapplication.app.main.login.repository.LoginRemoteRepoImpl
import com.example.profileapplication.app.main.login.usecase.LoginUseCase
import com.example.profileapplication.app.main.login.usecase.LoginUseCaseImpl
import com.example.profileapplication.app.main.profile.repository.ProfileRemoteRepo
import com.example.profileapplication.app.main.profile.repository.ProfileRemoteRepoImpl
import com.example.profileapplication.app.main.profile.usecase.ProfileUseCase
import com.example.profileapplication.app.main.profile.usecase.ProfileUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ImplementationInterfaceModule {

    @Provides
    @Singleton
    fun provideProfileRemoteRepo(profileRemoteRepoImpl: ProfileRemoteRepoImpl): ProfileRemoteRepo {
        return profileRemoteRepoImpl
    }

    @Provides
    @Singleton
    fun provideProfileUseCase(profileUseCaseImpl: ProfileUseCaseImpl): ProfileUseCase {
        return profileUseCaseImpl
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(loginUseCaseImpl: LoginUseCaseImpl): LoginUseCase {
        return loginUseCaseImpl
    }

    @Provides
    @Singleton
    fun provideLoginRemoteRepo(loginRemoteRepoImpl: LoginRemoteRepoImpl): LoginRemoteRepo {
        return loginRemoteRepoImpl
    }

    @Provides
    @Singleton
    fun provideFeedbackUseCase(feedbackUseCaseImpl: FeedbackUseCaseImpl): FeedbackUseCase {
        return feedbackUseCaseImpl
    }

    @Provides
    @Singleton
    fun provideFeedbackRemoteRepo(feedbackRemoteRepoImpl: FeedbackRemoteRepoImpl): FeedbackRemoteRepo {
        return feedbackRemoteRepoImpl
    }
}