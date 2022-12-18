package com.example.attendance.db.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="subject_table")
data class Subject (
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var title:String,
    var full_title:String
)
