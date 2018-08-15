package com.pcforgeek.simplytodo.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import com.pcforgeek.simplytodo.R
import com.pcforgeek.simplytodo.data.NotesRepository
import com.pcforgeek.simplytodo.viewmodel.NotesListViewModel
import com.pcforgeek.simplytodo.viewmodel.ViewModelFactory
import io.reactivex.disposables.CompositeDisposable

private const val ARG_LABEL_ID = "labelId"

class NotesListByLabelFragment : Fragment() {

    private var labelId: Int? = null
    private val disposable = CompositeDisposable()
    private lateinit var repository: NotesRepository
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewModel: NotesListViewModel

    companion object {
        @JvmStatic
        fun newInstance(id: Int) =
                NotesListByLabelFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_LABEL_ID, id)
                    }
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            labelId = it.getInt(ARG_LABEL_ID)
        }
        repository = NotesRepository(activity!!.application)
        viewModelFactory = ViewModelFactory(repository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.notes_list_by_label_fragment, container, false)
        recyclerView = view.findViewById(R.id.notes_by_label_list)
        recyclerViewAdapter = NotesAdapter()
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

        recyclerView.adapter = recyclerViewAdapter
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NotesListViewModel::class.java)
        val observing = viewModel.getNotesWithLabelId(labelId!!).subscribe {
            it?.let {
                Log.i("LabelFrag", "new batch of notes received ${it.size}")

                if (it.isEmpty()) {
                    Toast.makeText(context, "No Notes for this tag", Toast.LENGTH_SHORT).show()
                } else {
                    (recyclerViewAdapter as NotesAdapter).setUpdatedNotesList(it)
                }
            }
        }
        disposable.add(observing)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }


}
