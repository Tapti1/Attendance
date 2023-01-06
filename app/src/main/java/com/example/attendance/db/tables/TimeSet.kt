package com.example.attendance.db.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="time_sets")
data class TimeSet (
    @PrimaryKey(autoGenerate = true)
    var time_set_id:Int,
    var title:String
)
