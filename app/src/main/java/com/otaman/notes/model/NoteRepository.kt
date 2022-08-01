package com.otaman.notes.model

import androidx.lifecycle.LiveData
import com.otaman.notes.model.room.NoteDao

class NoteRepository private constructor(private val noteDao: NoteDao) {
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

    companion object {
        private var instance: NoteRepository? = null
        fun getInstance(noteDao: NoteDao): NoteRepository? {
            if(instance == null) {
                instance = NoteRepository(noteDao)
            }
            return instance
        }
    }
}