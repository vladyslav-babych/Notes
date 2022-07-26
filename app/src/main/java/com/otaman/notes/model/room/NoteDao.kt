package com.otaman.notes.model.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.otaman.notes.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: Note)

    @Delete
    fun deleteNote(note: Note)

    @Query("SELECT * FROM Note ORDER BY title ASC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM Note WHERE title LIKE :searchQuery OR content LIKE :searchQuery")
    fun searchNote(searchQuery: String): Flow<List<Note>>

    @Update
    fun updateNote(note: Note)
}