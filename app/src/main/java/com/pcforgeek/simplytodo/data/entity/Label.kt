package com.pcforgeek.simplytodo.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "label")
data class Label(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "label_id")
                 val labelId: Int?,
                 @ColumnInfo(name = "label_name")
                 val label: String)