package com.jy.firebaseNoteApp.data.models

data class User(
    val username: String,
    val email: String
) {
    companion object {
        fun fromMap(map: Map<*, *>): User =
            User(
                username = map["username"].toString(),
                email = map["email"].toString()
            )
    }
}
