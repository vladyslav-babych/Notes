package com.otaman.notes.model

import androidx.lifecycle.LiveData
import com.otaman.notes.model.room.NoteDatabase

object NoteRepository {
    private val noteDao = NoteDatabase.getDatabase(/*context*/).getNoteDao()

    fun getAllNotes(): LiveData<List<Note>> = noteDao.getAllNotes()

    fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }

    fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }

    fun updateNote(note: Note) {
        noteDao.updateNote(note)
    }
}