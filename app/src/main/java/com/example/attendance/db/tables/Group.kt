package com.example.attendance.db.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="groups")
data class Group (
    @PrimaryKey(autoGenerate = true)
    var group_id:Int,
    var title:String
)