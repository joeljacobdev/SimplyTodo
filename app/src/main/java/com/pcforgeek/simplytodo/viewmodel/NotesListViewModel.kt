package com.pcforgeek.simplytodo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.pcforgeek.simplytodo.data.entity.Notes
import com.pcforgeek.simplytodo.data.NotesRepository
import io.reactivex.Flowable

class NotesListViewModel(private val repository: NotesRepository) : ViewModel() {
    // is mutableLiveData only needed for single note or for the list
    // mutable only is needed when the developer need to make changes to data not the app
    private val notesList: LiveData<List<Notes>> = repository.getAllNotes()

    fun getAllNotes(): LiveData<List<Notes>> {
        return notesList
    }

    fun getNotesWithLabelId(labelId: Int): Flowable<List<Notes>> {
        return repository.getNotesWithLabelId(labelId)
    }



}
