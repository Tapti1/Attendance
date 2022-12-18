package com.example.attendance.db.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="group_table")
data class Group (
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var title:String
)