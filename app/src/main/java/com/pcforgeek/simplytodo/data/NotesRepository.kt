package com.pcforgeek.simplytodo.data

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.pcforgeek.simplytodo.data.dao.NoteDAO
import com.pcforgeek.simplytodo.data.database.NotesDatabase
import com.pcforgeek.simplytodo.data.entity.Notes
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.Flowable


class NotesRepository(application: Application) {
    private val notesDAO: NoteDAO
    private val notesList: LiveData<List<Notes>>

    init {
        val notesdb = NotesDatabase.getInstance(application)
        notesDAO = notesdb!!.notesDataDao()
        notesList = notesDAO.getAllNotes()
    }


    //returns the cached words as LiveData. Room executes all queries on a separate thread.
    //Observed LiveData notifies the observer when the data changes.
    fun getAllNotes(): LiveData<List<Notes>> {
        return notesList
    }

    fun deleteNote(notesData: Notes) {
        deleteAsyncTask(notesDAO).execute(notesData)
    }

    fun insertNote(notesData: Notes) {
        insertAsyncTask(notesDAO).execute(notesData)
    }

    fun updateNote(notesData: Notes) {
        updateAsyncTask(notesDAO).execute(notesData)
    }

    fun getNotes(uuid: Long): Flowable<Notes> {
        val notes = Flowable.fromCallable<Notes>
                    { notesDAO.getNote(uuid) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        return notes
    }

    class updateAsyncTask(private val notesDAO: NoteDAO) : AsyncTask<Notes, Unit, Unit>() {
        override fun doInBackground(vararg params: Notes) {
            notesDAO.updateNote(params[0])
        }
    }

    class insertAsyncTask(private val notesDAO: NoteDAO) : AsyncTask<Notes, Unit, Unit>() {
        override fun doInBackground(vararg params: Notes) {
            notesDAO.insertNote(params[0])
        }
    }

    class deleteAsyncTask(private val notesDAO: NoteDAO) : AsyncTask<Notes, Unit, Unit>() {
        override fun doInBackground(vararg params: Notes) {
            notesDAO.deleteNote(params[0])
        }
    }

}