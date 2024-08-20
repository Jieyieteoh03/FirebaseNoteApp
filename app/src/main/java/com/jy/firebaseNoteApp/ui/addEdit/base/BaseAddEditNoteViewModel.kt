package com.jy.firebaseNoteApp.ui.addEdit.base

import com.jy.firebaseNoteApp.ui.base.BaseViewModel

abstract class BaseAddEditNoteViewModel: BaseViewModel() {
    abstract fun submit(title: String, content: String, backgroundColor: Int)
}