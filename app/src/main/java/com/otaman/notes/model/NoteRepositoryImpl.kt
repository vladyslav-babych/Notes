package com.otaman.notes.model

import androidx.lifecycle.LiveData
import com.otaman.notes.model.room.NoteDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao,
    private val ioDispatcher: CoroutineDispatcher
): NoteRepository {
    override fun getAllNotes(): LiveData<List<Note>> = noteDao.getAllNotes()

    override suspend fun insertNote(note: Note) {
        withContext(ioDispatcher) {
            noteDao.insertNote(note)
        }
    }

    override suspend fun deleteNote(note: Note) {
        withContext(ioDispatcher) {
            noteDao.deleteNote(note)
        }
    }

    override suspend fun updateNote(note: Note) {
        withContext(ioDispatcher) {
            noteDao.updateNote(note)
        }
    }
}