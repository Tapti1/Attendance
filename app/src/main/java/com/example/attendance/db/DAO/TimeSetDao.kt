package com.example.attendance.db.DAO

import androidx.room.*
import com.example.attendance.db.tables.*

@Dao
interface TimeSetDao {
    @Insert
    fun insert(timeSet: TimeSet)
    @Query("SELECT * FROM time_sets")
    fun getAll():List<TimeSet>
    @Update
    fun update(timeSet: TimeSet)
    @Delete
    fun delete(timeSet: TimeSet)
    @Query("SELECT * FROM time_sets WHERE time_set_id=:this_id")
    fun get(this_id:Int): TimeSet
}