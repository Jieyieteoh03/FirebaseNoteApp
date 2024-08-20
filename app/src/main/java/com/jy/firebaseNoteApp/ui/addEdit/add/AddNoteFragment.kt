package com.jy.firebaseNoteApp.ui.addEdit.add

import android.view.View
import androidx.fragment.app.viewModels
import com.jy.firebaseNoteApp.R
import com.jy.firebaseNoteApp.ui.addEdit.base.BaseAddEditNoteFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNoteFragment: BaseAddEditNoteFragment() {
    override val viewModel: AddNoteViewModel by viewModels()

    override fun onBindView(view: View) {
        super.onBindView(view)
        binding?.mbSubmit?.text = context?.getString(R.string.add)
    }
}