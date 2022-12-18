package com.example.attendance.db.DAO

import androidx.room.*
import com.example.attendance.db.tables.*

@Dao
interface TimeSetDao {
    @Insert
    fun insert(timeSet: TimeSet)
    @Query("SELECT * FROM time_set_table")
    fun getAll():List<TimeSet>
    @Update
    fun update(timeSet: TimeSet)
    @Delete
    fun delete(timeSet: TimeSet)
    @Query("SELECT * FROM time_set_table WHERE id=:this_id")
    fun get(this_id:Int): TimeSet
}