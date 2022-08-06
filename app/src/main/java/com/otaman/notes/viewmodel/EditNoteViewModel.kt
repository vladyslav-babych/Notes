package com.otaman.notes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otaman.notes.model.Note
import com.otaman.notes.model.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    application: Application,
    private val repository: NoteRepository
): AndroidViewModel(application) {

    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateNote(note)
    }

    fun addNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertNote(note)
    }
}