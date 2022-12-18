package com.example.attendance.db.DAO

import androidx.room.*
import com.example.attendance.db.tables.*

@Dao
interface SubjectDao {
    @Insert
    fun insert(subject: Subject)
    @Query("SELECT * FROM subject_table")
    fun getAll():List<Subject>
    @Update
    fun update(subject: Subject)
    @Delete
    fun delete(subject: Subject)
    @Query("SELECT * FROM subject_table WHERE id=:this_id")
    fun get(this_id:Int): Subject
}