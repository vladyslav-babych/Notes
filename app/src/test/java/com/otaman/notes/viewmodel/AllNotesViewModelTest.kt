package com.otaman.notes.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.otaman.notes.model.FakeNoteRepository
import com.otaman.notes.model.Note
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
internal class AllNotesViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var allNotesViewModel: AllNotesViewModel
    private val note = Note(title = "Title1", content = "Content1")
    private val note1 = Note(title = "Title2", content = "Content2")
    private val note2 = Note(title = "Title3", content = "Content3")

    private suspend fun insertNotes() {
        fakeNoteRepository.insertNote(note)
        fakeNoteRepository.insertNote(note1)
        fakeNoteRepository.insertNote(note2)
    }

    @Before
    fun setup() {
        fakeNoteRepository = FakeNoteRepository()
        allNotesViewModel = AllNotesViewModel(fakeNoteRepository)
    }

    @Test
    fun getAllNotes_returnsListOfAllNotes() = runTest {
        insertNotes()
        advanceUntilIdle()

        val actualNotes = fakeNoteRepository.getAllNotes().getOrAwaitValue()
        val expectedNotes = listOf(note, note1, note2)

        assertThat(expectedNotes, equalTo(actualNotes))
    }

    @Test
    fun deleteNote_deletesNote() = runTest {
        insertNotes()
        allNotesViewModel.deleteNote(note)
        advanceUntilIdle()

        val expectedNotes = listOf(note1, note2)
        val actualNotes = fakeNoteRepository.notes

        assertThat(expectedNotes, equalTo(actualNotes))
    }

    @Test
    fun searchNote_returnsListOfMatchedNotes() = runTest {
        insertNotes()
        val searchQuery = "title3"
        allNotesViewModel.searchNote(searchQuery)
        advanceUntilIdle()

        val expectedNotes = allNotesViewModel.searchResults.getOrAwaitValue()
        val matchedNotes = listOf(note2)

        assertThat(expectedNotes, equalTo(matchedNotes))
    }
}


