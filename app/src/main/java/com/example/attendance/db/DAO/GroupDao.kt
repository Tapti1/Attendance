package com.example.attendance.db.DAO

import androidx.room.*
import com.example.attendance.db.tables.*

@Dao
interface GroupDao {
    @Insert
    fun insert(group: Group)
    @Query("SELECT * FROM groups")
    fun getAll():List<Group>
    @Update
    fun update(group: Group)
    @Delete
    fun delete(group: Group)
    @Query("SELECT * FROM groups WHERE group_id=:this_id")
    fun get(this_id:Int): Group
}