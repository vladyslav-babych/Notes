package com.otaman.notes.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.otaman.notes.model.Note
import com.otaman.notes.model.NoteRepository
import com.otaman.notes.model.room.NoteDatabase
import com.otaman.notes.view.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class AllNotesViewModel(application: Application): AndroidViewModel(application) {
    private val dao = NoteDatabase.getDatabase(application).getNoteDao()
    private val repository: NoteRepository = NoteRepository(dao)
    private val _searchResults = MutableLiveData<List<Note>>()
    val searchResults: LiveData<List<Note>> = _searchResults
    val allNotes: LiveData<List<Note>> = repository.getAllNotes()

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteNote(note)
    }

    fun searchNote(searchNote: String) = viewModelScope.launch(Dispatchers.Default) {
        val result = allNotes.value?.filter { note ->
            note.title.contains(searchNote, true) || note.content.contains(searchNote, true)
        }.orEmpty()

        _searchResults.postValue(result)
    }
}