package com.otaman.notes.di

import android.content.Context
import androidx.room.Room
import com.otaman.notes.model.room.NoteDao
import com.otaman.notes.model.room.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Scope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NoteDatabaseModule {

    @Provides
    fun provideNoteDao(database: NoteDatabase): NoteDao {
        return database.getNoteDao()
    }

    @Provides
    @Singleton
    fun provideNoteDatabase(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            "note_database"
        ).build()
    }
}