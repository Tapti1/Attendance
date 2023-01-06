package com.example.attendance.db.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="students")
data class Student (
    @PrimaryKey(autoGenerate = true)
    var student_id:Int,
    var first_name:String,
    var second_name:String,
    var patronymic:String,
    var group_id:Int    //add FK
)
