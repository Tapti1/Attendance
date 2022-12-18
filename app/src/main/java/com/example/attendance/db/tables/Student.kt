package com.example.attendance.db.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="student_table")
data class Student (
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var firstName:String,
    var secondName:String,
    var patronymic:String,
    var group_id:Int    //add FK
)
