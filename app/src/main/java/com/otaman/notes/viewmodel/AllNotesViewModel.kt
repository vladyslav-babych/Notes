package com.otaman.notes.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.otaman.notes.model.Note
import com.otaman.notes.model.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllNotesViewModel @Inject constructor(
    application: Application,
    private val repository: NoteRepository
): AndroidViewModel(application) {

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