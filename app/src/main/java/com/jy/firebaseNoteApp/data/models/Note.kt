package com.jy.firebaseNoteApp.data.models

data class Note(
    val id: String? = null,
    val title: String,
    val content: String,
    val backgroundColor: Int
) {
    fun toMap(): Map<String, Any?> =
        mapOf(
            "title" to title,
            "content" to content,
            "backgroundColor" to backgroundColor
        )

    companion object {
        fun fromMap(map: Map<*, *>): Note =
            Note(
                title = map["title"].toString(),
                content = map["content"].toString(),
                backgroundColor = (map["backgroundColor"] as Long).toInt()
            )
    }
}
