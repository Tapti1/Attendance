package com.example.attendance.db.tables

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName="attendances")
data class Attendance (
    @PrimaryKey(autoGenerate = true)
    var attendance_id:Int,
    var student_id: Int,
    var lesson_id: Int,
    var status: Boolean
)
