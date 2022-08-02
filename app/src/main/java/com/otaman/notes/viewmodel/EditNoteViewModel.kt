package com.otaman.notes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.otaman.notes.model.Note
import com.otaman.notes.model.NoteRepository
import com.otaman.notes.model.room.NoteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditNoteViewModel(application: Application): AndroidViewModel(application) {
    private val dao = NoteDatabase.getInstance(application).getNoteDao()
    private val repository = NoteRepository.getInstance(dao)

    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateNote(note)
    }

    fun addNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertNote(note)
    }
}