package com.example.attendance.db.DAO

import androidx.room.*
import com.example.attendance.db.tables.*

@Dao
interface StudentDao {
    @Insert
    fun insert(student: Student)
    @Query("SELECT * FROM student_table")
    fun getAll():List<Student>
    @Update
    fun update(student: Student)
    @Delete
    fun delete(student: Student)
    @Query("SELECT * FROM student_table WHERE id=:this_id")
    fun get(this_id:Int): Student
}