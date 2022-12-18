package com.example.attendance.db
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.attendance.db.tables.*
import com.example.attendance.db.DAO.*


@Database(entities = [Student::class, Group::class,Subject::class,TimeSet::class,Lesson::class,Attendance::class], version = 1)
abstract class MyDataBase:RoomDatabase() {
    abstract fun myStudentDao(): StudentDao
    abstract fun myGroupDao():GroupDao
    abstract fun mySubjectDao():SubjectDao
    abstract fun myLessonDao():LessonDao
    abstract fun myTimeSetDao():TimeSetDao
    abstract fun myAttendanceDao():AttendanceDao
}