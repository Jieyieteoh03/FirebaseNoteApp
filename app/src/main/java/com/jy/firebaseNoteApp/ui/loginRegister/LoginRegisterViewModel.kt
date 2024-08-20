package com.jy.firebaseNoteApp.ui.loginRegister

import android.content.res.ColorStateList
import androidx.lifecycle.viewModelScope
import com.jy.firebaseNoteApp.R
import com.jy.firebaseNoteApp.core.Constants.EMAIL_ERR
import com.jy.firebaseNoteApp.core.Constants.EMAIL_REG
import com.jy.firebaseNoteApp.core.Constants.LOGIN
import com.jy.firebaseNoteApp.core.Constants.NAME_ERR
import com.jy.firebaseNoteApp.core.Constants.NAME_REG
import com.jy.firebaseNoteApp.core.Constants.PASS_ERR
import com.jy.firebaseNoteApp.core.Constants.PASS_REG
import com.jy.firebaseNoteApp.core.Constants.REGISTER
import com.jy.firebaseNoteApp.core.services.AuthService
import com.jy.firebaseNoteApp.core.utils.ResourceProvider
import com.jy.firebaseNoteApp.core.utils.Utils.validate
import com.jy.firebaseNoteApp.data.models.User
import com.jy.firebaseNoteApp.data.models.ValidationField
import com.jy.firebaseNoteApp.data.repositories.UserRepo
import com.jy.firebaseNoteApp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginRegisterViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val authService: AuthService,
    private val resourceProvider: ResourceProvider
): BaseViewModel() {
    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            errorHandler {
                authService.login(email, password)
            }?.let {
                _success.emit(
                    resourceProvider.getFormattedString(
                        R.string.success_message, LOGIN
                    )
                )
            }
        }
    }

    fun register(
        username: String,
        email: String,
        password: String,
        password2: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val error = validate(
                ValidationField(username, NAME_REG, NAME_ERR),
                ValidationField(email, EMAIL_REG, EMAIL_ERR),
                ValidationField(password + password2, PASS_REG, PASS_ERR)
            )
            if(error == null) {
                errorHandler {
                    authService.createUser(email, password)
                    userRepo.createUser(User(username, email))
                }?.let {
                    _success.emit(
                        resourceProvider.getFormattedString(
                            R.string.success_message, REGISTER
                        )
                    )
                }
            } else _error.emit(error)
        }
    }

    fun setColor(color: String): ColorStateList =
        resourceProvider.getColor(
            when(color) {
                "blue" -> R.color.blue
                "black" -> R.color.black
                "white" -> R.color.white
                else -> R.color.grey
            }
        )
}