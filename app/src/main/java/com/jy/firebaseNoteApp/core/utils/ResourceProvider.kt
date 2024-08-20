package com.jy.firebaseNoteApp.core.utils

import android.content.Context
import android.content.res.ColorStateList
import androidx.annotation.ColorRes
import androidx.annotation.StringRes

class ResourceProvider(
    private val context: Context
) {
    fun getColor(@ColorRes resId: Int): ColorStateList = context.getColorStateList(resId)

    fun getString(@StringRes resId: Int): String = context.getString(resId)

    fun getFormattedString(@StringRes resId: Int, args: String): String =
        context.getString(resId, args)
}