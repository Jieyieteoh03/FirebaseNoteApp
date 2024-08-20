package com.jy.firebaseNoteApp.ui.loginRegister

import android.view.View
import androidx.fragment.app.viewModels
import com.jy.firebaseNoteApp.R
import com.jy.firebaseNoteApp.core.Constants.LOGIN
import com.jy.firebaseNoteApp.core.Constants.REGISTER
import com.jy.firebaseNoteApp.core.utils.Utils.setVisibility
import com.jy.firebaseNoteApp.databinding.FragmentLoginRegisterBinding
import com.jy.firebaseNoteApp.ui.base.BaseFragment
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginRegisterFragment : BaseFragment<FragmentLoginRegisterBinding>() {
    override val viewModel: LoginRegisterViewModel by viewModels()
    override fun getLayoutResource(): Int = R.layout.fragment_login_register
    private var state: String = LOGIN

    override fun onBindView(view: View) {
        super.onBindView(view)
        binding?.run {
            tvHeader.text = state.uppercase()
            mbLogin.setOnClickListener {
                if(state == LOGIN)
                    viewModel.login(
                        etEmail.text.toString(),
                        etPassword.text.toString()
                    )
                else toggleState(it as MaterialButton)
            }
            mbRegister.setOnClickListener {
                if(state == REGISTER)
                    viewModel.register(
                        etUsername.text.toString(),
                        etEmail.text.toString(),
                        etPassword.text.toString(),
                        etPassword2.text.toString()
                    )
                else toggleState(it as MaterialButton)
            }
        }
    }

    override fun onBindData(view: View) {
        super.onBindData(view)
    }

    private fun toggleState(button: MaterialButton) {
        val blue = viewModel.setColor("blue")
        val white = viewModel.setColor("white")
        val black = viewModel.setColor("black")
        binding?.run {
            state = if(state == LOGIN) REGISTER else LOGIN
            tvHeader.text = state.uppercase()
            etUsername.setText("")
            etEmail.setText("")
            etPassword.setText("")
            etUsername.setVisibility(state == LOGIN)
            etPassword2.setVisibility(state == LOGIN)
            val button2 = if(button.id == mbLogin.id) mbRegister else mbLogin
            button.backgroundTintList = blue
            button2.backgroundTintList = white
            button.setTextColor(white)
            button2.setTextColor(black)
        }
    }
}