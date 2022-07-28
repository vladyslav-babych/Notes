package com.otaman.notes.model

import androidx.lifecycle.LiveData
import com.otaman.notes.model.room.NoteDao
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {
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