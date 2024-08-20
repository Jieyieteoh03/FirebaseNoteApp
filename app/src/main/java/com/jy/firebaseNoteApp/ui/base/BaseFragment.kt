package com.jy.firebaseNoteApp.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.jy.firebaseNoteApp.R
import com.jy.firebaseNoteApp.core.Constants.ADD
import com.jy.firebaseNoteApp.core.Constants.EDIT
import com.jy.firebaseNoteApp.core.Constants.ERROR
import com.jy.firebaseNoteApp.core.Constants.INFO
import com.jy.firebaseNoteApp.core.Constants.LOGIN
import com.jy.firebaseNoteApp.core.Constants.REGISTER
import com.jy.firebaseNoteApp.core.Constants.SUCCESS
import com.google.android.material.snackbar.Snackbar
import com.jy.firebaseNoteApp.ui.loginRegister.LoginRegisterFragmentDirections
import kotlinx.coroutines.launch

abstract class BaseFragment<T : ViewDataBinding>: Fragment() {
    protected abstract val viewModel: BaseViewModel
    protected var binding: T? = null
    protected abstract fun getLayoutResource(): Int

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(getLayoutResource(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBindView(view)
        onBindData(view)
    }

    protected open fun onBindView(view: View) { binding = DataBindingUtil.bind(view) }

    protected open fun onBindData(view: View) {
        lifecycleScope.launch {
            viewModel.success.collect {
                showSnackBar(view, it, SUCCESS)
                if(it.contains(ADD) || it.contains(EDIT)) findNavController().popBackStack()
                else if(it.contains(LOGIN) || it.contains(REGISTER))
                    findNavController().navigate(
                        LoginRegisterFragmentDirections.actionLoginRegistertoHome()
                    )
            }
        }
        lifecycleScope.launch {
            viewModel.finish.collect { showToast(it) }
        }
        lifecycleScope.launch {
            viewModel.error.collect { showSnackBar(view, it, ERROR) }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showSnackBar(
        view: View, message: String, type: String
    ) {
        val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        val backgroundTint = when(type) {
            INFO -> R.color.blue
            ERROR -> R.color.red
            SUCCESS -> R.color.green
            else -> R.color.grey
        }
        snackBar.setBackgroundTint(
            ContextCompat.getColor(requireContext(), backgroundTint)
        ).show()
    }
}