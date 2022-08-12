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
    private val note = Note(id = "1", title = "Title1", content = "Content1")
    private val note1 = Note(id = "1", title = "Title2", content = "Content2")

    @Before
    fun setup() {
        fakeNoteRepository = FakeNoteRepository()
        allNotesViewModel = AllNotesViewModel(fakeNoteRepository)
    }

    @Test
    fun deleteNote_deletesNote() = runTest {
        allNotesViewModel.deleteNote(note)
        advanceUntilIdle()

        val expectedNotes = emptyList<Note>()
        val actualNotes = fakeNoteRepository.notes

        assertThat(expectedNotes, equalTo(actualNotes))
    }

    @Test
    fun searchNote_returnsListOfMatchedNotes() = runTest {
        advanceUntilIdle()

        val allNotes = mutableListOf(note, note1)
        val matchedNotes = mutableListOf<Note>()
        val expectedNotes = listOf(note)
        val searchTitleString = "le1"
        val searchContentString = "tent1"

        val searchResult = allNotes.filter {
            it.title.contains(searchTitleString, true) ||
            it.content.contains(searchContentString, true)
        }
        matchedNotes.addAll(searchResult)

        assertThat(expectedNotes, equalTo(matchedNotes))
    }
}


