package com.jy.firebaseNoteApp.data.repositories

import com.jy.firebaseNoteApp.core.services.AuthService
import com.jy.firebaseNoteApp.data.models.Note
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class NoteRepo(
    private val authService: AuthService
) {
    private fun getCollection(): CollectionReference {
        val uid = authService.getUid() ?: throw Exception("User doesn't exist!")
        return Firebase.firestore.collection("root_db/$uid/notes")
    }

    fun getAllNotes() = callbackFlow<List<Note>> {
        val listener = getCollection().addSnapshotListener { value, error ->
            if(error != null) throw error
            val notes = mutableListOf<Note>()
            value?.documents?.map { snapshot ->
                snapshot.data?.let {
                    notes.add(Note.fromMap(it).copy(id = snapshot.id))
                }
            }
            trySend(notes)
        }
        awaitClose { listener.remove() }
    }

    suspend fun createNote(note: Note): String? {
        val res = getCollection().add(note.toMap()).await()
        return res?.id
    }

    suspend fun deleteNote(id: String) { getCollection().document(id).delete().await() }

    suspend fun updateNote(note: Note) {
        getCollection().document(note.id!!).set(note.toMap()).await()
    }

    suspend fun getNoteById(id: String): Note? {
        val res = getCollection().document(id).get().await()
        return res.data?.let { Note.fromMap(it).copy(id = res.id) }
    }
}