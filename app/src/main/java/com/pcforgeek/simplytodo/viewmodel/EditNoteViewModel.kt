package com.pcforgeek.simplytodo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.pcforgeek.simplytodo.data.entity.Notes
import com.pcforgeek.simplytodo.data.NotesRepository
import io.reactivex.Flowable

class EditNoteViewModel(private val repository: NotesRepository) : ViewModel() {
    private val notesList: LiveData<List<Notes>> = repository.getAllNotes()

    fun getAllNotes(): LiveData<List<Notes>> {
        return notesList
    }

    fun insertNote(notesData: Notes) {
        repository.insertNote(notesData)
    }

    fun updateNote(notesData: Notes){
        repository.updateNote(notesData)
    }

    fun getNote(uuid: Long): Flowable<Notes> {
        return repository.getNotes(uuid)
    }
}
