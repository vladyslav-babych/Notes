package com.otaman.notes.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FakeNoteRepository: NoteRepository {
    private val notes = mutableListOf<Note>()
    private val observableNotes = MutableLiveData<List<Note>>(notes)

    override fun getAllNotes(): LiveData<List<Note>> {
        return observableNotes
    }

    override suspend fun insertNote(note: Note) {
        notes.add(note)
        observableNotes.postValue(notes)
    }

    override suspend fun deleteNote(note: Note) {
        notes.remove(note)
        refreshLiveData()
    }

    override suspend fun updateNote(note: Note) {
        for(index in notes.indices) {
            if(notes[index].id == note.id)
                notes[index] = note
        }
        refreshLiveData()
    }

    private fun refreshLiveData() {
        observableNotes.postValue(notes)
    }
}