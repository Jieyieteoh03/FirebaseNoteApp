package com.jy.firebaseNoteApp.data.models

data class ValidationField(
    val value: String,
    val regExp: String,
    val errorMessage: String
)