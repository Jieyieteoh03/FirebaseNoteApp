package com.jy.firebaseNoteApp.ui.addEdit.base


import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.children
import com.jy.firebaseNoteApp.R
import com.jy.firebaseNoteApp.databinding.FragmentAddEditNoteBinding
import com.jy.firebaseNoteApp.ui.base.BaseFragment

abstract class BaseAddEditNoteFragment : BaseFragment<FragmentAddEditNoteBinding>() {
    abstract override val viewModel: BaseAddEditNoteViewModel
    override fun getLayoutResource(): Int = R.layout.fragment_add_edit_note
    private val colors =
        listOf(R.color.green, R.color.blue, R.color.red, R.color.purple, R.color.yellow)

    override fun onBindView(view: View) {
        super.onBindView(view)
        binding?.run {
            rgColor.children.forEachIndexed { idx, view ->
                val colorSelector = createColorSelector(colors[idx])
                view.background = colorSelector
            }
            mbSubmit.setOnClickListener {
                val backgroundColor = checkSelectedColor(rgColor.checkedRadioButtonId)
                viewModel.submit(
                    etTitle.text.toString(), etContent.text.toString(), backgroundColor
                )
            }
        }
    }

    private fun checkSelectedColor(selectedId: Int): Int =
        binding?.run {
            when(selectedId) {
                rbBlue.id -> colors[1]
                rbRed.id -> colors[2]
                rbPurple.id -> colors[3]
                rbYellow.id -> colors[4]
                else -> colors[0]
            }
        } ?: R.color.grey

    protected fun checkSelectedRadioButtonId(selectedColor: Int): Int? =
        binding?.run {
            when(selectedColor) {
                colors[1] -> rbBlue.id
                colors[2] -> rbRed.id
                colors[3] -> rbPurple.id
                colors[4] -> rbYellow.id
                else -> rbGreen.id
            }
        }

    private fun createColorSelector(color: Int): StateListDrawable =
        StateListDrawable().apply {
            addState(
                intArrayOf(android.R.attr.state_checked),
                GradientDrawable().apply {
                    setColor(ContextCompat.getColor(requireContext(), color))
                    setStroke(10, ContextCompat.getColor(requireContext(), R.color.black))
                    cornerRadius = 2f
                }
            )
            addState(
                intArrayOf(),
                GradientDrawable().apply {
                    setColor(ContextCompat.getColor(requireContext(), color))
                    setStroke(10, ContextCompat.getColor(requireContext(), R.color.white))
                    cornerRadius = 2f
                }
            )
        }
}