package com.jy.firebaseNoteApp.ui.addEdit.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.jy.firebaseNoteApp.R
import com.jy.firebaseNoteApp.core.Constants.EDIT
import com.jy.firebaseNoteApp.core.utils.ResourceProvider
import com.jy.firebaseNoteApp.data.models.Note
import com.jy.firebaseNoteApp.data.repositories.NoteRepo
import com.jy.firebaseNoteApp.ui.addEdit.base.BaseAddEditNoteViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    state: SavedStateHandle,
    private val repo: NoteRepo,
    private val resourceProvider: ResourceProvider
): BaseAddEditNoteViewModel() {
    private val _note = MutableStateFlow<Note?>(null)
    val note: StateFlow<Note?> = _note
    private val noteId = state.get<String>("noteId")

    init {
        viewModelScope.launch(Dispatchers.IO) {
            noteId?.let {
                errorHandler { _note.value = repo.getNoteById(it) }
            }
        }
    }

    override fun submit(
        title: String, content: String, backgroundColor: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            if(title.isNotEmpty() && content.isNotEmpty()) {
                note.value?.let {
                    errorHandler {
                        repo.updateNote(
                            it.copy(
                                title = title,
                                content = content,
                                backgroundColor = backgroundColor
                            )
                        )
                        _success.emit(
                            resourceProvider.getFormattedString(R.string.success_message, EDIT)
                        )
                    }
                }
            } else _error.emit(resourceProvider.getString(R.string.empty_message))
        }
    }
}