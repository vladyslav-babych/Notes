package com.otaman.notes.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.*
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.otaman.notes.R
import com.otaman.notes.model.Note
import com.otaman.notes.databinding.ActivityMainBinding
import com.otaman.notes.viewmodel.AllNotesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnNoteClick, OnNoteDeleteClick {
    private lateinit var binding: ActivityMainBinding
    private val allNotesViewModel  by viewModels<AllNotesViewModel>()
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUi()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val searchItem = menu.findItem(R.id.actionSearch)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = "Search"

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(searchedText: String): Boolean {
                searchNote(searchedText)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun searchNote(query: String) {
        allNotesViewModel.searchNote(query)
    }

    private fun initUi() {
        val recyclerView = binding.rvNotesList
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = NoteAdapter(this, this)
        recyclerView.adapter = adapter

        allNotesViewModel.allNotes.observe(this) { list ->
            adapter.updateNotesList(list)
        }

        allNotesViewModel.searchResults.observe(this) { list ->
            adapter.updateNotesList(list)
        }

        binding.fabAddNote.setOnClickListener {
            val intent = Intent(this@MainActivity, EditNoteActivity::class.java)
            intent.putExtra(NOTE_TYPE, NoteType.ADD)
            startActivity(intent)
        }
    }

    override fun onNoteClick(note: Note) {
        val intent = Intent(this@MainActivity, EditNoteActivity::class.java)
        intent.putExtra(NOTE_TYPE, NoteType.EDIT)
        intent.putExtra(NOTE, note)
        startActivity(intent)
    }

    override fun onNoteDeleteClick(note: Note) {
        allNotesViewModel.deleteNote(note)
        Toast.makeText(this, "${note.title} Deleted", Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val NOTE_TYPE = "com.otaman.notes.note_type"
        const val NOTE = "com.otaman.notes.note"
    }
}