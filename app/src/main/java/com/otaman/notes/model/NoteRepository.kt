package com.otaman.notes.model

import androidx.lifecycle.LiveData
import com.otaman.notes.model.room.NoteDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NoteRepository (
    private val noteDao: NoteDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    fun getAllNotes(): LiveData<List<Note>> = noteDao.getAllNotes()

    suspend fun insertNote(note: Note) = withContext(ioDispatcher) {
        noteDao.insertNote(note)
    }

    suspend fun deleteNote(note: Note) = withContext(ioDispatcher) {
        noteDao.deleteNote(note)
    }

    suspend fun updateNote(note: Note) = withContext(ioDispatcher) {
        noteDao.updateNote(note)
    }
}