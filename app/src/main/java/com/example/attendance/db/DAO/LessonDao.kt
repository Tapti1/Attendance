package com.example.attendance.db.DAO

import androidx.room.*
import com.example.attendance.db.tables.*

@Dao
interface LessonDao {
    @Insert
    fun insert(lesson: Lesson)
    @Query("SELECT * FROM lessons")
    fun getAll():List<Lesson>
    @Update
    fun update(lesson: Lesson)
    @Delete
    fun delete(lesson: Lesson)
    @Query("SELECT * FROM lessons WHERE lesson_id=:this_id")
    fun get(this_id:Int): Lesson
    @Query("SELECT * FROM lessons WHERE date=:cur_date")
    fun getLessonsByDate(cur_date:String): List<Lesson>
}