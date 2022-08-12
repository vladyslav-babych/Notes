package com.otaman.notes.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.otaman.notes.model.FakeNoteRepository
import com.otaman.notes.model.Note
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
internal class EditNoteViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var editNoteViewModel: EditNoteViewModel
    private val note = Note(id = "1", title = "Title1", content = "Content1")
    private val note1 = Note(id = "1", title = "Title2", content = "Content2")

    @Before
    fun setup() {
        fakeNoteRepository = FakeNoteRepository()
        editNoteViewModel = EditNoteViewModel(fakeNoteRepository)
    }

    @Test
    fun addNote_addsNote() = runTest {
        editNoteViewModel.addNote(note = note)
        advanceUntilIdle()

        val expectedNotes = listOf(note)
        val actualNotes = fakeNoteRepository.notes

        assertThat(expectedNotes, equalTo(actualNotes))
    }

    @Test
    fun updateNote_updatesNote() = runTest {
        editNoteViewModel.addNote(note = note)
        editNoteViewModel.updateNote(note = note1)
        advanceUntilIdle()

        val expectedNotes = listOf(note1)
        val actualNotes = fakeNoteRepository.notes

        assertThat(expectedNotes, equalTo(actualNotes))
    }
}