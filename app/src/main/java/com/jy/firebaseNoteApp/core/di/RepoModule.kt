package com.jy.firebaseNoteApp.core.di

import com.jy.firebaseNoteApp.core.services.AuthService
import com.jy.firebaseNoteApp.data.repositories.NoteRepo
import com.jy.firebaseNoteApp.data.repositories.UserRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {
    @Provides
    @Singleton
    fun provideNoteRepo(authService: AuthService): NoteRepo = NoteRepo(authService)

    @Provides
    @Singleton
    fun provideUserRepo(authService: AuthService): UserRepo = UserRepo(authService)
}