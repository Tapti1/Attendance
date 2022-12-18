package com.example.attendance.db.DAO

import androidx.room.*
import com.example.attendance.db.tables.*

@Dao
interface AttendanceDao {
    @Insert
    fun insert(attendance: Attendance)
    @Query("SELECT * FROM attendance_table")
    fun getAll():List<Attendance>
    @Update
    fun update(attendance: Attendance)
    @Delete
    fun delete(attendance: Attendance)
    @Query("SELECT * FROM attendance_table WHERE id=:this_id")
    fun get(this_id:Int): Attendance
    @Query("SELECT * FROM attendance_table INNER JOIN lesson_table ON " +
            "attendance_table.lesson_id=lesson_table.id " +
            "WHERE lesson_table.subject_id=:cur_subject AND " +
            "lesson_table.date=:cur_date AND " +
            "lesson_table.time_set_id=:cur_time ")
    fun getAttendancebySubjectDate(cur_date:String,cur_subject:Int,cur_time:Int):List<Attendance>
}