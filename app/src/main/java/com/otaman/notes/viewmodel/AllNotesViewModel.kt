package com.otaman.notes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otaman.notes.model.Note
import com.otaman.notes.model.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllNotesViewModel @Inject constructor(
    private val repository: NoteRepository
): ViewModel() {

    private val _searchResults = MutableLiveData<List<Note>>()
    val searchResults: LiveData<List<Note>> = _searchResults
    val allNotes: LiveData<List<Note>> = repository.getAllNotes()

    fun deleteNote(note: Note) = viewModelScope.launch {
        repository.deleteNote(note)
    }

    fun searchNote(searchNote: String) = viewModelScope.launch {
        val result = allNotes.value?.filter { note ->
            note.title.contains(searchNote, true) || note.content.contains(searchNote, true)
        }.orEmpty()

        _searchResults.postValue(result)
    }
}