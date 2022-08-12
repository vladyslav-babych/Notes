package com.otaman.notes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otaman.notes.Event
import com.otaman.notes.model.Note
import com.otaman.notes.model.NoteRepository
import com.otaman.notes.model.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val repository: NoteRepository
): ViewModel() {

    private val _addNoteEvent = MutableLiveData<Event<Resource<Note>>>()
    val addNoteEvent: LiveData<Event<Resource<Note>>> = _addNoteEvent

    private val _updateNoteEvent = MutableLiveData<Event<Resource<Note>>>()
    val updateNoteEvent: LiveData<Event<Resource<Note>>> = _updateNoteEvent

    fun updateNote(note: Note) = viewModelScope.launch {
        repository.updateNote(note)
    }

    fun addNote(note: Note) = viewModelScope.launch {
        repository.insertNote(note)
    }
}