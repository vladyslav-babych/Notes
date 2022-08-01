package com.otaman.notes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otaman.notes.model.Note
import com.otaman.notes.model.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditNoteViewModel: ViewModel() {
    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        NoteRepository.updateNote(note)
    }

    fun addNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        NoteRepository.insertNote(note)
    }
}