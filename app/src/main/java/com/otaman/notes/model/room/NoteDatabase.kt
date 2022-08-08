package com.otaman.notes.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.otaman.notes.model.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun getNoteDao(): NoteDao
}