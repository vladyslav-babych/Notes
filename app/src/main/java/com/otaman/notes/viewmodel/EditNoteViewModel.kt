package com.otaman.notes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otaman.notes.model.Note
import com.otaman.notes.model.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val repository: NoteRepository
): ViewModel() {

    fun updateNote(note: Note) = viewModelScope.launch {
        repository.updateNote(note)
    }

    fun addNote(note: Note) = viewModelScope.launch {
        repository.insertNote(note)
    }
}