package com.example.attendance.db
import android.content.Context
import androidx.room.Room

class DbHandler(context: Context) {
    val db= Room.databaseBuilder(
        context,
        MyDataBase::class.java,"attendance_database"
    ).build()
    fun getDataBase():MyDataBase{
        return db
    }
}