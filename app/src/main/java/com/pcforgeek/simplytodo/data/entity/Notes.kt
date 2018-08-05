package com.pcforgeek.simplytodo.data.entity

import androidx.room.*
import com.pcforgeek.simplytodo.data.DateConverter
import java.util.*

@Entity(tableName = "notes")
class Notes {
    @PrimaryKey(autoGenerate = true)
    var uuid: Long? = null
    @ColumnInfo(name = "date")
    @TypeConverters(DateConverter::class)
    var date: Date? = null
    @ColumnInfo(name = "tile")
    var title: String = ""
    @ColumnInfo(name = "content")
    var content: String = ""

    constructor(uuid: Long?, date: Date, title: String, content: String) {
        this.uuid = uuid
        this.date = date
        this.title = title
        this.content = content
    }

}