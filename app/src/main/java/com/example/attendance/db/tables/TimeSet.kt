package com.example.attendance.db.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="time_set_table")
data class TimeSet (
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var title:String
)
