package com.otaman.notes.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.otaman.notes.model.Note
import com.otaman.notes.R
import com.otaman.notes.databinding.ListItemNoteBinding

class NoteAdapter(
    val context: Context,
    val onNoteDeleteClick: OnNoteDeleteClick,
    val onNoteClick: OnNoteClick
): RecyclerView.Adapter<NoteAdapter.NotesViewHolder>() {
    private val notes = ArrayList<Note>()

    inner class NotesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ListItemNoteBinding.bind(itemView)

        val tvTitle = binding.tvNoteTitle
        val tvContent = binding.tvNoteContent
        val ibDelete = binding.ibDeleteNote

        fun showNote(position: Int) {
            tvTitle.text = notes[position].title
            tvContent.text = notes[position].content
            ibDelete.setOnClickListener {
                onNoteDeleteClick.onNoteDeleteClick(notes[position])
            }
            itemView.setOnClickListener {
                onNoteClick.onNoteClick(notes[position])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_note, parent, false)
        return NotesViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.showNote(position)

    }

    override fun getItemCount() = notes.size

    fun updateNotesList(newNotesList: List<Note>) {
        this.notes.clear()
        this.notes.addAll(newNotesList)
        notifyDataSetChanged()
    }
}

interface OnNoteClick {
    fun onNoteClick(note: Note)
}

interface OnNoteDeleteClick {
    fun onNoteDeleteClick(note: Note)
}