package com.pcforgeek.simplytodo.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.pcforgeek.simplytodo.data.entity.Notes

/**
 * Created by
 *      JOEL JACOB(@pcforgeek)
 *       on 26/7/18.
 */
@Dao
interface NoteDAO {
    @Query("SELECT * from notes")
    fun getAllNotes(): LiveData<List<Notes>>//Entity's list

    @Insert(onConflict = REPLACE)
    fun insertNote(notesData: Notes)

    @Update
    fun updateNote(notesData: Notes)

    @Query("SELECT * from notes where uuid LIKE :noteId")
    fun getNote(noteId: Long?): Notes

    @Query("DELETE from notes")
    fun deleteAllNotes()

    @Delete
    fun deleteNote(notesData: Notes)

    @Query("SELECT * from notes where label_id = :labelId")
    fun getNotesWithLabelId(labelId: Int): List<Notes>

}