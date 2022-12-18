package com.example.attendance.db.DAO

import androidx.room.*
import com.example.attendance.db.tables.*

@Dao
interface GroupDao {
    @Insert
    fun insert(group: Group)
    @Query("SELECT * FROM group_table")
    fun getAll():List<Group>
    @Update
    fun update(group: Group)
    @Delete
    fun delete(group: Group)
    @Query("SELECT * FROM group_table WHERE id=:this_id")
    fun get(this_id:Int): Group
}