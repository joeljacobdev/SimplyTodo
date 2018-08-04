package com.pcforgeek.simplytodo.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.pcforgeek.simplytodo.data.dao.NoteDAO
import com.pcforgeek.simplytodo.data.entity.Notes
import com.pcforgeek.simplytodo.utils.ioThread

/**
 * Created by
 *      JOEL JACOB(@pcforgeek)
 *       on 26/7/18.
 */
@Database(entities = arrayOf(Notes::class), version = 1)
abstract class NotesDatabase : RoomDatabase() {

    //We have to create an abstract method for every DAO class that we create. This is really important.
    abstract fun notesDataDao(): NoteDAO

    companion object {
        private val PRE_POP_DB = listOf<Notes>(
                Notes(null, "", "title1", "content"),
                Notes(null, "", "title2", "content"),
                Notes(null, "", "title3", "content"),
                Notes(null, "", "title4", "content"),
                Notes(null, "", "title5", "mutliline mutliline mutliline mutliline mutliline mutliline mutliline mutliline content"),
                Notes(null, "", "title6", "content"))
        @Volatile
        private var INSTANCE: NotesDatabase? = null

        fun getInstance(context: Context): NotesDatabase? {
            if (INSTANCE == null) {
                synchronized(NotesDatabase::class) {
                    //why class.java
                    INSTANCE = Room.databaseBuilder(context.applicationContext, NotesDatabase::class.java, "notes")
                            .addCallback(object : Callback() {
                                override fun onCreate(db: SupportSQLiteDatabase) {
                                    super.onCreate(db)
                                    ioThread {
                                        for (data in PRE_POP_DB)
                                            getInstance(context)!!.notesDataDao().insertNote(data)
                                    }
                                }
                            })
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}