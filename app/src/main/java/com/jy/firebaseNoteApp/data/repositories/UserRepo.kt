package com.jy.firebaseNoteApp.data.repositories

import com.jy.firebaseNoteApp.core.services.AuthService
import com.jy.firebaseNoteApp.data.models.User
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class UserRepo(
    private val authService: AuthService
) {
    private fun getUid(): String = authService.getUid() ?: throw Exception("User doesn't exist!")

    private fun getCollection(): CollectionReference =
        Firebase.firestore.collection("users")

    suspend fun createUser(user: User) {
        getCollection().document(getUid()).set(user).await()
    }

    suspend fun getUserById(): User? {
        val res = getCollection().document(getUid()).get().await()
        return res.data?.let { User.fromMap(it) }
    }

    fun logout() { authService.logout() }
}