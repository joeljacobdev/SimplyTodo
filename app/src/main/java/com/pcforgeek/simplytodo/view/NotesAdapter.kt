package com.pcforgeek.simplytodo.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pcforgeek.simplytodo.R
import com.pcforgeek.simplytodo.data.entity.Notes

class NotesAdapter : RecyclerView.Adapter<NoteHolder>() {
    private var notesList: List<Notes> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_holder, parent, false)
        return NoteHolder(view, parent.context)
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.bindData(notesList[position])
    }

    fun setUpdatedNotesList(updatedList: List<Notes>) {
        notesList = updatedList
        notifyDataSetChanged()

    }
}