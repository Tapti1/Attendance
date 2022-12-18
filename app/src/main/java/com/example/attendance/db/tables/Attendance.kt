package com.example.attendance.db.tables

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName="attendance_table")
data class Attendance (
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var student_id: Int,
    var lesson_id: Int,
    var status: Boolean
)
