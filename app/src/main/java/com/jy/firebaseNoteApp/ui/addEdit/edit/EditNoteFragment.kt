package com.jy.firebaseNoteApp.ui.addEdit.edit

import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.jy.firebaseNoteApp.R
import com.jy.firebaseNoteApp.core.utils.Utils.setVisibility
import com.jy.firebaseNoteApp.ui.addEdit.base.BaseAddEditNoteFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditNoteFragment: BaseAddEditNoteFragment() {
    override val viewModel: EditNoteViewModel by viewModels()

    override fun onBindView(view: View) {
        super.onBindView(view)
        binding?.mbSubmit?.text = context?.getString(R.string.edit)
    }

    override fun onBindData(view: View) {
        super.onBindData(view)
        lifecycleScope.launch {
            viewModel.note.collect {
                if(it != null) {
                    binding?.run {
                        etTitle.setText(it.title)
                        etContent.setText(it.content)
                        checkSelectedRadioButtonId(it.backgroundColor)?.let { radioBtn ->
                            rgColor.check(radioBtn)
                        }
                        llAddEdit.setBackgroundColor(
                            ContextCompat.getColor(requireContext(), it.backgroundColor)
                        )
                    }
                }
            }
        }
    }
}