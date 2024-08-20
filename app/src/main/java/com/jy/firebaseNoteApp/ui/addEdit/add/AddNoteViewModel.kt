package com.jy.firebaseNoteApp.ui.addEdit.add

import androidx.lifecycle.viewModelScope
import com.jy.firebaseNoteApp.R
import com.jy.firebaseNoteApp.core.Constants.ADD
import com.jy.firebaseNoteApp.core.utils.ResourceProvider
import com.jy.firebaseNoteApp.data.models.Note
import com.jy.firebaseNoteApp.data.repositories.NoteRepo
import com.jy.firebaseNoteApp.ui.addEdit.base.BaseAddEditNoteViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val repo: NoteRepo,
    private val resourceProvider: ResourceProvider
): BaseAddEditNoteViewModel() {
    override fun submit(
        title: String, content: String, backgroundColor: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            if(title.isNotEmpty() && content.isNotEmpty()) {
                errorHandler {
                    repo.createNote(
                        Note(
                            title = title,
                            content = content,
                            backgroundColor = backgroundColor
                        )
                    )
                }?.let {
                    _success.emit(
                        resourceProvider.getFormattedString(R.string.success_message, ADD)
                    )
                }
            } else _error.emit(resourceProvider.getString(R.string.empty_message))
        }
    }
}