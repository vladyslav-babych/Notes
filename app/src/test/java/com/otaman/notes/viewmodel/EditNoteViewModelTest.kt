package com.otaman.notes.viewmodel

import com.otaman.notes.model.FakeNoteRepository
import com.otaman.notes.model.Note
import com.otaman.notes.model.Status
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
internal class EditNoteViewModelTest {
//    @get:Rule

    private lateinit var editNoteViewModel: EditNoteViewModel
    private val note = Note(title = "Title1", content = "Content1")

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setup() {
        editNoteViewModel = EditNoteViewModel(FakeNoteRepository())
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun addNote_addsNote() = runBlocking {
        editNoteViewModel.addNote(note = note)

        val value = editNoteViewModel.addNoteEvent.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled()?.status, equalTo(Status.SUCCESS))
    }
}