package com.jy.firebaseNoteApp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.jy.firebaseNoteApp.core.utils.ResourceProvider
import com.jy.firebaseNoteApp.data.models.Note
import com.jy.firebaseNoteApp.databinding.ItemNoteBinding
import javax.inject.Inject

class NoteAdapter @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private var notes: List<Note>
): RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    var listener: Listener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder =
        NoteViewHolder(
            ItemNoteBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) =
        holder.bind(notes[position])

    override fun getItemCount(): Int = notes.size

    fun setNotes(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    inner class NoteViewHolder(
        private val binding: ItemNoteBinding
    ): ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.run {
                tvTitle.text = note.title
                val content = note.content
                tvContent.text =
                    if(content.length > 50) content.slice(0 .. 50) + "..."
                    else content
                cvNote.setCardBackgroundColor(resourceProvider.getColor(note.backgroundColor))
                cvNote.setOnClickListener { listener?.onClickItem(note) }
                ivDelete.setOnClickListener { listener?.onDeleteItem(note) }
            }
        }
    }

    interface Listener {
        fun onClickItem(note: Note)
        fun onDeleteItem(note: Note)
    }
}