package com.pcforgeek.simplytodo.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.pcforgeek.simplytodo.data.DateConverter
import com.pcforgeek.simplytodo.data.dao.LabelDAO
import com.pcforgeek.simplytodo.data.dao.NoteDAO
import com.pcforgeek.simplytodo.data.entity.Label
import com.pcforgeek.simplytodo.data.entity.Notes
import com.pcforgeek.simplytodo.utils.ioThread
import java.util.*

@Database(entities = arrayOf(Notes::class, Label::class), version = 1)
@TypeConverters(DateConverter::class)
abstract class NotesDatabase : RoomDatabase() {

    //We have to create an abstract method for every DAO class that we create. This is really important.
    abstract fun notesDataDao(): NoteDAO

    abstract fun labelDao(): LabelDAO

    companion object {
        private val PRE_POP_DB = listOf<Notes>(
                Notes(null, Date(), "title1", "content"),
                Notes(null, Date(), "title2", "content"),
                Notes(null, Date(), "title3", "content"),
                Notes(null, Date(), "title4", "content"),
                Notes(null, Date(), "title5", "mutliline mutliline mutliline mutliline mutliline mutliline mutliline mutliline content"),
                Notes(null, Date(), "title6", "content"))
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
                                    ioThread {
                                        getInstance(context)!!.labelDao().addLabel(Label(null, "default"))
                                        getInstance(context)!!.labelDao().addLabel(Label(null, "first-tag"))
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