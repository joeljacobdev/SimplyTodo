package com.pcforgeek.simplytodo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pcforgeek.simplytodo.data.NotesRepository

/**
 * Created by
 *      JOEL JACOB(@pcforgeek)
 *       on 26/7/18.
 */
class ViewModelFactory(repo: NotesRepository): ViewModelProvider.Factory {
    private val repository = repo

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NotesListViewModel::class.java)) {
                return NotesListViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(EditNoteViewModel::class.java)) {
            return EditNoteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}