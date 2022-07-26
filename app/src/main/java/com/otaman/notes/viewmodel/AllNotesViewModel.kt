package com.otaman.notes.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.otaman.notes.model.Note
import com.otaman.notes.model.NoteRepository
import com.otaman.notes.model.room.NoteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AllNotesViewModel(application: Application): AndroidViewModel(application) {
    private val dao = NoteDatabase.getDatabase(application).getNoteDao()
    private val repository: NoteRepository = NoteRepository(dao)
    val allNotes: LiveData<List<Note>> = repository.getAllNotes()

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteNote(note)
    }

    fun searchNote(searchQuery: String): LiveData<List<Note>> {
        return repository.searchNote(searchQuery).asLiveData()
    }
}