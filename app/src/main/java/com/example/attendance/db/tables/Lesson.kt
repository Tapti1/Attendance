package com.example.attendance.db.tables

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName="lesson_table")
data class Lesson (
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var date: String,
    var time_set_id:Int,
    var subject_id: Int
)
