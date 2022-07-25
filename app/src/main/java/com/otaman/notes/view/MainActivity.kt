package com.otaman.notes.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.otaman.notes.model.Note
import com.otaman.notes.databinding.ActivityMainBinding
import com.otaman.notes.viewmodel.AllNotesViewModel

class MainActivity : AppCompatActivity(), OnNoteClick, OnNoteDeleteClick {
    private lateinit var binding: ActivityMainBinding
    private lateinit var allNotesViewModel: AllNotesViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.rvNotesList
        initUi()
    }

    private fun initUi() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = NoteAdapter(this, this, this)
        recyclerView.adapter = adapter

        allNotesViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[AllNotesViewModel::class.java]

        allNotesViewModel.allNotes.observe(this, Observer { list ->
            list.let {
                adapter.updateNotesList(it)
            }
        })

        binding.fabAddNote.setOnClickListener {
            val intent = Intent(this@MainActivity, EditNoteActivity::class.java)
            intent.putExtra("noteType", "NewNote")
            startActivity(intent)
        }
    }

    override fun onNoteClick(note: Note) {
        val intent = Intent(this@MainActivity, EditNoteActivity::class.java)
        intent.putExtra("noteType", "Edit")
        intent.putExtra("noteTitle", note.title)
        intent.putExtra("noteContent", note.content)
        intent.putExtra("noteId", note.id)
        startActivity(intent)
    }

    override fun onNoteDeleteClick(note: Note) {
        allNotesViewModel.deleteNote(note)
        Toast.makeText(this, "${note.title} Deleted", Toast.LENGTH_SHORT).show()
    }
}