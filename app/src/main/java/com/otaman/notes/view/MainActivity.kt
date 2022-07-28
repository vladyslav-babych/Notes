package com.otaman.notes.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.otaman.notes.R
import com.otaman.notes.model.Note
import com.otaman.notes.databinding.ActivityMainBinding
import com.otaman.notes.viewmodel.AllNotesViewModel

class MainActivity : AppCompatActivity(), OnNoteClick, OnNoteDeleteClick {
    private lateinit var binding: ActivityMainBinding
    private lateinit var allNotesViewModel: AllNotesViewModel
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

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if(query != null) searchNote(query)
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    private fun searchNote(query: String) {
        val searchQuery = "%$query%"
        allNotesViewModel.searchNote(searchQuery).observe(this@MainActivity) { list ->
            list.let { adapter.updateNotesList(it) }
        }
    }

    private fun initUi() {
        val recyclerView = binding.rvNotesList
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = NoteAdapter(this, this, this)
        recyclerView.adapter = adapter

        allNotesViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[AllNotesViewModel::class.java]

        allNotesViewModel.allNotes.observe(this) { list ->
            list.let {
                adapter.updateNotesList(it)
            }
        }

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