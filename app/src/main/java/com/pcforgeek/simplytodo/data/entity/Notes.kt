package com.pcforgeek.simplytodo.data.entity

import androidx.room.*
import com.pcforgeek.simplytodo.data.DateConverter
import java.util.*

@Entity(tableName = "notes", foreignKeys = [ForeignKey(entity = Label::class,
        parentColumns = arrayOf("label_id"),
        childColumns = arrayOf("label_id"),
        onDelete = ForeignKey.SET_NULL)])
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
    @ColumnInfo(name = "label_id")
    var labelId: Int? = null

    @Ignore
    constructor(uuid: Long?, date: Date, title: String, content: String) {
        this.uuid = uuid
        this.date = date
        this.title = title
        this.content = content
    }

    constructor(uuid: Long?, date: Date, title: String, content: String, labelId: Int?) {
        this.uuid = uuid
        this.date = date
        this.title = title
        this.content = content
        this.labelId = labelId
    }

}