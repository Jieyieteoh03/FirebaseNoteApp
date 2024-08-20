package com.jy.firebaseNoteApp.core.utils

import android.util.Log
import android.view.View
import com.jy.firebaseNoteApp.data.models.ValidationField

object Utils {
    fun debugLog(): (Any) -> Unit = { Log.d("debugging", it.toString()) }

    fun View.setVisibility(condition: Boolean) {
        this.visibility = if(condition) View.GONE else View.VISIBLE
    }

    fun validate(vararg fields: ValidationField): String? {
        fields.forEachIndexed { index, field ->
            val value = field.value
            if(index == fields.lastIndex) {
                val mid = value.length / 2
                val password = value.substring(0, mid)
                val password2 = value.substring(mid)
                if(password != password2) return "Both passwords must match!"
            }
            if(!Regex(field.regExp).matches(value)) return field.errorMessage
        }
        return null
    }
}