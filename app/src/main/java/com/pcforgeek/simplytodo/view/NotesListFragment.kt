package com.pcforgeek.simplytodo.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.pcforgeek.simplytodo.viewmodel.NotesListViewModel
import com.pcforgeek.simplytodo.R
import com.pcforgeek.simplytodo.viewmodel.ViewModelFactory
import com.pcforgeek.simplytodo.data.entity.Notes
import com.pcforgeek.simplytodo.data.NotesRepository


class NotesListFragment : Fragment() {

    private lateinit var repository: NotesRepository
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: RecyclerView.Adapter<*>

    companion object {
        fun newInstance() = NotesListFragment()
    }

    private lateinit var viewModel: NotesListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = NotesRepository(activity!!.application)
        viewModelFactory = ViewModelFactory(repository)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.notes_list_fragment, container, false)
        recyclerView = view.findViewById(R.id.notes_list)
        recyclerViewAdapter = NotesAdapter()
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

        recyclerView.adapter = recyclerViewAdapter
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NotesListViewModel::class.java)

        viewModel.getAllNotes().observe(this, object : Observer<List<Notes>> {
            override fun onChanged(updatedList: List<Notes>?) {
                updatedList?.let {
                    (recyclerViewAdapter as NotesAdapter).setUpdatedNotesList(updatedList)
                }
            }
        })
    }

}
