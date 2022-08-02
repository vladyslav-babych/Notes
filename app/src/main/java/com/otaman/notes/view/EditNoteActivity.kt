package com.otaman.notes.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import com.otaman.notes.model.Note
import com.otaman.notes.databinding.ActivityNoteDetailsBinding
import com.otaman.notes.view.MainActivity.Companion.NOTE
import com.otaman.notes.view.MainActivity.Companion.NOTE_TYPE
import com.otaman.notes.viewmodel.EditNoteViewModel

class EditNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteDetailsBinding
    private val editNoteViewModel by viewModels<EditNoteViewModel>()
    private lateinit var tvEditTitle: EditText
    private lateinit var tvEditContent: EditText
    private lateinit var bSave: Button
    private lateinit var noteType: NoteType
    private var noteId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteType = intent.getSerializableExtra(NOTE_TYPE) as NoteType
        initButtonAndEditTexts()
        getNote()
        initClickListeners()
    }

    private fun initButtonAndEditTexts() {
        tvEditTitle = binding.tvEditNoteTitle
        tvEditContent = binding.tvEditNoteContent
        bSave = binding.bSaveNote
    }

    private fun getNote() {
        if (noteType == NoteType.EDIT) {
            val note = intent.getParcelableExtra<Note>(NOTE)!!
            val noteTitle = note.title
            val noteContent = note.content
            noteId = note.id
            bSave.text = "UPDATE"
            tvEditTitle.setText(noteTitle)
            tvEditContent.setText(noteContent)
        } else {
            bSave.text = "SAVE"
        }
    }

    private fun initClickListeners() {
        bSave.setOnClickListener {
            val noteTitle = tvEditTitle.text.toString()
            val noteContent = tvEditContent.text.toString()
            if (noteType == NoteType.EDIT) {
                if (noteTitle.isNotEmpty() && noteContent.isNotEmpty()) {
                    val updatedNote = Note(id = noteId, title = noteTitle, content = noteContent)
                    editNoteViewModel.updateNote(updatedNote)
                    Toast.makeText(this, "Note Updated", Toast.LENGTH_LONG).show()
                }
                else {
                    Toast.makeText(this, "All fields have to be filled", Toast.LENGTH_LONG).show()
                }
            } else {
                if (noteTitle.isNotEmpty() && noteContent.isNotEmpty()) {
                    editNoteViewModel.addNote(Note(title = noteTitle, content = noteContent))
                    Toast.makeText(this, "$noteTitle Added", Toast.LENGTH_LONG).show()
                }
                else {
                    Toast.makeText(this, "All fields have to be filled", Toast.LENGTH_LONG).show()
                }
            }
            this.finish()
        }
    }
}