package com.pcforgeek.simplytodo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.pcforgeek.simplytodo.data.NotesRepository
import com.pcforgeek.simplytodo.data.entity.Label

class LabelViewModel(private val repository: NotesRepository): ViewModel() {


    private val labelList: LiveData<List<Label>> = repository.getAllLabel()

    fun getAllLabel() : LiveData<List<Label>> {
        return labelList
    }

}