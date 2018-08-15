package com.pcforgeek.simplytodo.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.pcforgeek.simplytodo.data.entity.Label

@Dao
interface LabelDAO {
    @Query("SELECT * from label order by label_name asc")
    fun getAllLabel(): LiveData<List<Label>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addLabel(label: Label)

    @Delete
    fun deleteLabel(label: Label)

    @Update
    fun editLabel(label: Label)
}