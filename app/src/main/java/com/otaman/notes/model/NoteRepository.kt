package com.otaman.notes.model

import androidx.lifecycle.LiveData

interface NoteRepository {
    fun getAllNotes(): LiveData<List<Note>>
    suspend fun insertNote(note: Note)
    suspend fun deleteNote(note: Note)
    suspend fun updateNote(note: Note)
}