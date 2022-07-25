package com.otaman.notes.app

import android.app.Application
import androidx.room.Room
import com.otaman.notes.model.room.NoteDatabase

class NoteApplication: Application() {

    companion object {
        lateinit var database: NoteDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            this, NoteDatabase::class.java, "note_database"
        ).build()
    }
}