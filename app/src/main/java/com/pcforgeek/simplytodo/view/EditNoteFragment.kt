package com.pcforgeek.simplytodo.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pcforgeek.simplytodo.viewmodel.EditNoteViewModel
import com.pcforgeek.simplytodo.R
import com.pcforgeek.simplytodo.viewmodel.ViewModelFactory
import com.pcforgeek.simplytodo.data.NotesRepository
import com.pcforgeek.simplytodo.data.entity.Notes
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.edit_note_fragment.*
import kotlinx.android.synthetic.main.edit_note_fragment.view.*

private const val ARG_ACTION = "action"
private const val ARG_UUID = "uuid"

class EditNoteFragment : Fragment() {
    private lateinit var action: String
    private var uuid: Long? = null
    private val disposable = CompositeDisposable()

    companion object {
        fun newInstance(action: String, uuid: Long?) = EditNoteFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_ACTION, action)
                uuid?.let {
                    putLong(ARG_UUID, uuid)
                }
            }
        }
    }

    private lateinit var viewModel: EditNoteViewModel
    private lateinit var repository: NotesRepository
    private lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            action = it.getString(ARG_ACTION)
            uuid = it.getLong(ARG_UUID) ?: null
        }

        repository = NotesRepository(activity!!.application)
        viewModelFactory = ViewModelFactory(repository)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(EditNoteViewModel::class.java)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.edit_note_fragment, container, false)
        setupViewForUpdate(view)
        return view
    }

    private fun setupViewForUpdate(view: View) {
        if (action.contentEquals("UPDATE") && uuid != null) {
            val id = uuid!!
            val notes = viewModel.getNote(id)
            val observing = notes.subscribe {
                view.itemContent.text = SpannableStringBuilder(it.content)
                view.itemTitle.text = SpannableStringBuilder(it.title)
            }
            disposable.add(observing)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(EditNoteViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

    fun saveNote() {
        when (action) {
            "NEW" -> insertNote()
            "UPDATE" -> updateNote()
        }
    }

    private fun updateNote() {
        if (!itemContent.text.isNullOrBlank() || !itemTitle.text.isNullOrBlank()) {
            val notes = Notes(uuid, "", itemTitle.text.toString(), itemContent.text.toString())
            viewModel.updateNote(notes)
        }
    }

    private fun insertNote() {
        //blank will ignore whitespaces
        if (!itemContent.text.isNullOrBlank() || !itemTitle.text.isNullOrBlank()) {
            val notes = Notes(null, "", itemTitle.text.toString(), itemContent.text.toString())
            viewModel.insertNote(notes)
        }
    }

}
