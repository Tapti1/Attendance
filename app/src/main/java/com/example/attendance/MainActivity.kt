package com.example.attendance

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.attendance.db.DAO.*
import com.example.attendance.db.DbHandler
import com.example.attendance.db.MyStudentAdapter
import com.example.attendance.db.tables.*
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    val listAdapter= MyStudentAdapter(ArrayList(),ArrayList(),this)

    var curDate:String="27.11";

    var curSubject:Int=1;
    var curTimeSet:Int=1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        insertData()
        createChips()
    }
    override fun onResume() {
        super.onResume()
        updateResView()
    }

    override fun onStart() {
        super.onStart()
        updateResView()
    }
    fun init(){
        val rcStudentView:RecyclerView=findViewById(R.id.rcStudentView)
        rcStudentView.layoutManager= LinearLayoutManager(this)
        rcStudentView.adapter=listAdapter
    }
    fun insertData(){
        val dbh= DbHandler(applicationContext)
        val db=dbh.getDataBase()

        val myStudentDao: StudentDao =db.myStudentDao()
        val myTimeSetDao:TimeSetDao=db.myTimeSetDao()
        val mySubjectDao:SubjectDao=db.mySubjectDao()
        val myLessonDao:LessonDao=db.myLessonDao()
        val myGroupDao:GroupDao=db.myGroupDao()
        val myAttendanceDao:AttendanceDao=db.myAttendanceDao()

        val job: Job = GlobalScope.launch(Dispatchers.IO) {
            val student: Student = Student(0,"Ольга","Михайлова","Владимировна",1)
            myStudentDao.insert(student)

            val group:Group= Group(0,"05402")
            myGroupDao.insert(group)
            val timeSet:TimeSet=TimeSet(0,"8:00")
            myTimeSetDao.insert(timeSet)
            val timeSet2:TimeSet=TimeSet(0,"9:40")
            myTimeSetDao.insert(timeSet2)
            val subject:Subject=Subject(0,"ООП","Объектноориентированное программирование")
            mySubjectDao.insert(subject)
            val subject2:Subject=Subject(0,"БД","Базы Данных")
            mySubjectDao.insert(subject2)
            val lesson:Lesson= Lesson(0,"27.11",1,1)
            myLessonDao.insert(lesson)
            val lesson2:Lesson= Lesson(0,"27.11",2,2)
            myLessonDao.insert(lesson2)
            val attendance:Attendance=Attendance(0,1,1,true)
            myAttendanceDao.insert(attendance)
            val attendance2:Attendance=Attendance(0,1,2,false)
            myAttendanceDao.insert(attendance2)


            runOnUiThread{
            }
        }
    }
    fun updateResView(){
        val dbh= DbHandler(applicationContext)
        val db=dbh.getDataBase()
        val MyStudentDao: StudentDao =db.myStudentDao()
        val MyAttendanceDao:AttendanceDao=db.myAttendanceDao()

        val job: Job = GlobalScope.launch(Dispatchers.IO) {
            val students=MyStudentDao.getAll()
            val attendance=MyAttendanceDao.getAttendancebySubjectDate(curDate,curSubject,curTimeSet)
            runOnUiThread{
                listAdapter.updateAdapter(students,attendance)
            }
        }
    }
    fun createChips(){
        val dbh= DbHandler(applicationContext)
        val db=dbh.getDataBase()
        val myLessonDao:LessonDao=db.myLessonDao()
        val mySubjectDao:SubjectDao=db.mySubjectDao()
        val myTimeSetDao:TimeSetDao=db.myTimeSetDao()

        val job: Job = GlobalScope.launch(Dispatchers.IO) {
            val lessons=myLessonDao.getLessonsByDate(curDate)

            val groupChip:ChipGroup=findViewById(R.id.chipGroupSubject)
            for(i in lessons){

                val chip: Chip =Chip(groupChip.context)
                chip.text=mySubjectDao.get(i.subject_id).title.toString() +
                "("+ myTimeSetDao.get(i.time_set_id).title.toString()+")";
                chip.isCheckable=true
                chip.isClickable=true
                chip.setOnClickListener {
                    curSubject=i.subject_id
                    curTimeSet=i.time_set_id
                    updateResView()
                }
                groupChip.addView(chip)
            }
        }
        val groupChip:ChipGroup=findViewById(R.id.chipGroupSubject)
        val chip: Chip =Chip(groupChip.context)
        chip.text="OMG"
        chip.isCheckable=true
        chip.isClickable=true
        groupChip.addView(chip)
    }

}