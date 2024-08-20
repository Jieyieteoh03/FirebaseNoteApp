package com.jy.firebaseNoteApp.core

object Constants {
    // Identifiers
    const val LOGIN = "Login"
    const val LOGOUT = "Logout"
    const val REGISTER = "Register"
    const val SUBMIT = "Submit"
    const val ERROR = "Error"
    const val INFO = "Info"
    const val SUCCESS = "Success"

    // Operations
    const val ADD = "Add"
    const val EDIT = "Edit"
    const val DELETE = "Delete"

    // Validation RegExp & Error Messages
    const val NAME_REG = "[a-zA-Z ]{2,20}"
    const val NAME_ERR = "Username can only contain alphabets with a length of 2 to 20 each."
    const val EMAIL_REG = "[a-zA-Z0-9]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+"
    const val EMAIL_ERR = "Please enter a valid email. (e.g. johndoe123@gmail.com)"
    const val PASS_REG = "[a-zA-Z0-9#$%]{8,20}"
    const val PASS_ERR = "Password must have a length of 8 to 20, only (#$%) special characters are allowed."
}