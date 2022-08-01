package com.otaman.notes.app

import android.app.Application
import androidx.room.Room
import com.otaman.notes.model.room.NoteDatabase

object NoteApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Room.databaseBuilder(
            this, NoteDatabase::class.java, "note_database"
        ).build()
    }
}