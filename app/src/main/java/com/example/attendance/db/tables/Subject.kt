package com.example.attendance.db.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="subjects")
data class Subject (
    @PrimaryKey(autoGenerate = true)
    var subject_id:Int,
    var short_title:String,
    var full_title:String
)
