package com.pcforgeek.simplytodo.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

/**
 * Created by
 *      JOEL JACOB(@pcforgeek)
 *       on 26/7/18.
 */
@Entity(tableName = "notes")
data class Notes(@PrimaryKey(autoGenerate = true) val uuid: Long? = null,
                 @ColumnInfo(name = "date") val date: String = "",
                 @ColumnInfo(name = "tile") val title: String,
                 @ColumnInfo(name = "content") val content: String
)