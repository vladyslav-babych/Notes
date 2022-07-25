package com.otaman.notes.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.otaman.notes.model.Note
import com.otaman.notes.databinding.ActivityNoteDetailsBinding
import com.otaman.notes.viewmodel.EditNoteViewModel

class EditNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteDetailsBinding
    private lateinit var editNoteViewModel: EditNoteViewModel
    private lateinit var tvEditTitle: EditText
    private lateinit var tvEditContent: EditText
    private lateinit var bSave: Button
    private lateinit var noteType: String
    private var noteId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteType = intent.getStringExtra("noteType").orEmpty()
        initViewModel()
        tvEditTitle = binding.tvEditNoteTitle
        tvEditContent = binding.tvEditNoteContent
        bSave = binding.bSaveNote
        getNote()
        initClickListeners()
    }

    private fun initViewModel() {
        editNoteViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[EditNoteViewModel::class.java]
    }

    private fun getNote() {
        if (noteType == "Edit") {
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteContent = intent.getStringExtra("noteContent")
            noteId = intent.getStringExtra("noteId").orEmpty()
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
            if (noteType == "Edit") {
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