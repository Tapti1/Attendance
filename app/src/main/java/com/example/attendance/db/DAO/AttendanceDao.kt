package com.example.attendance.db.DAO

import androidx.room.*
import com.example.attendance.db.tables.*

@Dao
interface AttendanceDao {
    @Insert
    fun insert(attendance: Attendance)
    @Query("SELECT * FROM attendances")
    fun getAll():List<Attendance>
    @Update
    fun update(attendance: Attendance)
    @Delete
    fun delete(attendance: Attendance)
    @Query("SELECT * FROM attendances WHERE attendance_id=:this_id")
    fun get(this_id:Int): Attendance
    @Query("SELECT * FROM attendances INNER JOIN lessons ON " +
            "attendances.lesson_id=lessons.lesson_id " +
            "WHERE lessons.subject_id=:cur_subject AND " +
            "lessons.date=:cur_date AND " +
            "lessons.time_set_id=:cur_time ")
    fun getAttendancebySubjectDate(cur_date:String,cur_subject:Int,cur_time:Int):List<Attendance>
}