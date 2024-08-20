package com.jy.firebaseNoteApp.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

abstract class BaseViewModel: ViewModel() {
    protected val _finish = MutableSharedFlow<String>()
    val finish: SharedFlow<String> = _finish
    protected val _success: MutableSharedFlow<String> = MutableSharedFlow()
    val success: SharedFlow<String> = _success
    protected val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String> = _error

    suspend fun <T> errorHandler(
        function: suspend () -> T?
    ): T? = try {
        function()
    } catch (e: Exception) {
        _error.emit(e.message.toString())
        e.printStackTrace()
        null
    }
}