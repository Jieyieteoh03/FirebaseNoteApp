package com.jy.firebaseNoteApp.core.di

import com.jy.firebaseNoteApp.core.utils.ResourceProvider
import com.jy.firebaseNoteApp.ui.adapters.NoteAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {
    @Provides
    @Singleton
    fun provideNoteAdapter(
        resourceProvider: ResourceProvider
    ): NoteAdapter = NoteAdapter(resourceProvider, emptyList())
}