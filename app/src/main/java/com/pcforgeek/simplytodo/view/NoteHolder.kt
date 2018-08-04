package com.pcforgeek.simplytodo.view

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pcforgeek.simplytodo.R
import com.pcforgeek.simplytodo.data.entity.Notes

private const val EDIT_FRAG = "editFragment"

class NoteHolder(itemView: View, private val context: Context) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    private val contentText: TextView = itemView.findViewById(R.id.note_content)
    private val titleText: TextView = itemView.findViewById(R.id.note_title)
    private var uuid: Long = 0

    init {
        itemView.setOnClickListener(this)
    }

    fun bindData(note: Notes) {
        uuid = note.uuid!!
        titleText.text = note.title
        contentText.text = note.content
    }

    override fun onClick(view: View?) {
        //TODO fix fab behaviour on 'update'
        (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frag_container, EditNoteFragment.newInstance("UPDATE",uuid), EDIT_FRAG)
                .addToBackStack(null)
                .commit()
    }
}