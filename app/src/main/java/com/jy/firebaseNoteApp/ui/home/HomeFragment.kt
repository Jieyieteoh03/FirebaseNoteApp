package com.jy.firebaseNoteApp.ui.home

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.jy.firebaseNoteApp.R
import com.jy.firebaseNoteApp.core.utils.Utils.setVisibility
import com.jy.firebaseNoteApp.data.models.Note
import com.jy.firebaseNoteApp.databinding.FragmentHomeBinding
import com.jy.firebaseNoteApp.ui.adapters.NoteAdapter
import com.jy.firebaseNoteApp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val viewModel: HomeViewModel by viewModels()
    override fun getLayoutResource(): Int = R.layout.fragment_home
    @Inject
    lateinit var adapter: NoteAdapter

    override fun onBindView(view: View) {
        super.onBindView(view)
        setupAdapter()
        binding?.fabAdd?.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeToAddNote())
        }
        binding?.fabExit?.setOnClickListener {
            viewModel.logout()
            findNavController().navigate(HomeFragmentDirections.actionHomeToLoginRegister())
        }
    }

    override fun onBindData(view: View) {
        super.onBindData(view)
        lifecycleScope.launch {
            viewModel.getAllNotes().collect {
                adapter.setNotes(it)
                binding?.run {
                    rvNotes.setVisibility(it.isEmpty())
                    llEmpty.setVisibility(it.isNotEmpty())
                }
            }
        }
    }

    private fun setupAdapter() {
        adapter.listener = object: NoteAdapter.Listener {
            override fun onClickItem(note: Note) {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeToEditNote(note.id!!)
                )
            }
            override fun onDeleteItem(note: Note) { viewModel.deleteNote(note.id!!) }
        }
        binding?.rvNotes?.let { rv ->
            val spanCount = 2
            val orientation = RecyclerView.VERTICAL

            rv.adapter = adapter
            rv.layoutManager= StaggeredGridLayoutManager(spanCount,orientation)
        }
    }
}