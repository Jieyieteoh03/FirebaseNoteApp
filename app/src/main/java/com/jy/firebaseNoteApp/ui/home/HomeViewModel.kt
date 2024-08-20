package com.jy.firebaseNoteApp.ui.home

import androidx.lifecycle.viewModelScope
import com.jy.firebaseNoteApp.R
import com.jy.firebaseNoteApp.core.Constants.DELETE
import com.jy.firebaseNoteApp.core.Constants.LOGOUT
import com.jy.firebaseNoteApp.core.utils.ResourceProvider
import com.jy.firebaseNoteApp.data.models.Note
import com.jy.firebaseNoteApp.data.repositories.NoteRepo
import com.jy.firebaseNoteApp.data.repositories.UserRepo
import com.jy.firebaseNoteApp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val noteRepo: NoteRepo,
    private val userRepo: UserRepo,
    private val resourceProvider: ResourceProvider
): BaseViewModel() {
    fun getAllNotes(): Flow<List<Note>> = noteRepo.getAllNotes()

    fun deleteNote(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            errorHandler {
                noteRepo.deleteNote(id)
            }?.let {
                _finish.emit(
                    resourceProvider.getFormattedString(R.string.success_message, DELETE)
                )
            }
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            errorHandler {
                userRepo.logout()
                _finish.emit(
                    resourceProvider.getFormattedString(R.string.success_message, LOGOUT)
                )
            }
        }
    }
}