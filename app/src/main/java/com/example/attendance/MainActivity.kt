package com.example.attendance

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
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
import java.util.Calendar

class MainActivity : AppCompatActivity(),DatePickerDialog.OnDateSetListener {
    val listAdapter= MyStudentAdapter(ArrayList(),ArrayList(),this)

    var day:Int=0
    var mounth:Int=0
    var year:Int=0

    var curDate:String="7.1.2023"
    //var curDate:String="27.11.2022"
    var curSubject:Int=1
    var curTimeSet:Int=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        insertData()
        createChips()
        //getDate()
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
        //ввод каких=то начальных данных
        val dbh= DbHandler(applicationContext)
        val db=dbh.getDataBase()

        val myStudentDao: StudentDao =db.myStudentDao()
        val myTimeSetDao:TimeSetDao=db.myTimeSetDao()
        val mySubjectDao:SubjectDao=db.mySubjectDao()
        val myLessonDao:LessonDao=db.myLessonDao()
        val myGroupDao:GroupDao=db.myGroupDao()
        val myAttendanceDao:AttendanceDao=db.myAttendanceDao()

        val job: Job = GlobalScope.launch(Dispatchers.IO) {
            val listE=myStudentDao.getAll();

            if(listE.isEmpty()) {
                val student: Student = Student(0, "Ольга", "Михайлова", "Владимировна", 1)
                myStudentDao.insert(student)

                val group: Group = Group(0, "05402")
                myGroupDao.insert(group)
                val timeSet: TimeSet = TimeSet(0, "8:00")
                myTimeSetDao.insert(timeSet)
                val timeSet2: TimeSet = TimeSet(0, "9:40")
                myTimeSetDao.insert(timeSet2)
                val subject: Subject = Subject(0, "ООП", "Объектноориентированное программирование")
                mySubjectDao.insert(subject)
                val subject2: Subject = Subject(0, "БД", "Базы Данных")
                mySubjectDao.insert(subject2)
                val lesson: Lesson = Lesson(0, "27.11.2022", 1, 1)
                myLessonDao.insert(lesson)
                val lesson2: Lesson = Lesson(0, "27.11.2022", 2, 2)
                myLessonDao.insert(lesson2)
                val lesson3: Lesson = Lesson(0, "8.1.2023", 2, 2)
                myLessonDao.insert(lesson3)
                val attendance: Attendance = Attendance(0, 1, 1, true)
                myAttendanceDao.insert(attendance)
                val attendance2: Attendance = Attendance(0, 1, 2, false)
                myAttendanceDao.insert(attendance2)

                val lesson4: Lesson = Lesson(0, "7.1.2023", 2, 2)
                myLessonDao.insert(lesson4)
                val lesson5: Lesson = Lesson(0, "7.1.2023", 1, 1)
                myLessonDao.insert(lesson4)
                val attendance3: Attendance = Attendance(0, 1, 4, true)
                myAttendanceDao.insert(attendance3)

            }
        }

    }
    fun updateResView(){
        //Создаём DAO
        val dbh= DbHandler(applicationContext)
        val db=dbh.getDataBase()
        val MyStudentDao: StudentDao =db.myStudentDao()
        val MyAttendanceDao:AttendanceDao=db.myAttendanceDao()

        val job: Job = GlobalScope.launch(Dispatchers.IO) {
            //получаем студентов и занятия
            val students=MyStudentDao.getAll()
            val attendance=MyAttendanceDao.getAttendancebySubjectDate(curDate,curSubject,curTimeSet)
            runOnUiThread{
                //и обновляем rcView
                listAdapter.updateAdapter(students,attendance)
            }
        }
    }
    fun createChips() {
        //Создаём DAO
        val dbh = DbHandler(applicationContext)
        val db = dbh.getDataBase()
        val myLessonDao: LessonDao = db.myLessonDao()
        val mySubjectDao: SubjectDao = db.mySubjectDao()
        val myTimeSetDao: TimeSetDao = db.myTimeSetDao()

        //Добавляем чипы с предметами
        val job: Job = GlobalScope.launch(Dispatchers.IO) {
            //получаем все предметы по дате
            val lessons=myLessonDao.getLessonsByDate(curDate)

            val groupChip:ChipGroup=findViewById(R.id.chipGroupSubject)
            groupChip.removeAllViews()

            for(i in lessons){
                val chip: Chip =Chip(groupChip.context)     //сам чип
                //То, что в нём находится
                chip.text=mySubjectDao.get(i.subject_id).short_title.toString() +
                "("+ myTimeSetDao.get(i.time_set_id).title.toString()+")";
                chip.isCheckable=true
                chip.isClickable=true

                //При нажатии меняем предмет и время
                chip.setOnClickListener {
                    curSubject=i.subject_id
                    curTimeSet=i.time_set_id
                    updateResView()
                }
                groupChip.addView(chip)
            }
        }
        //просто какой-то чип
        val groupChip:ChipGroup=findViewById(R.id.chipGroupSubject)
        val chip: Chip =Chip(groupChip.context)
        chip.text="OMG"
        chip.isCheckable=true
        chip.isClickable=true
        groupChip.addView(chip)
    }
    fun getDate(){
        //получаем сегодняшнюю дату
        val calendar=Calendar.getInstance()

        day=calendar.get(Calendar.DAY_OF_MONTH)
        mounth=calendar.get(Calendar.MONTH)
        year=calendar.get(Calendar.YEAR)

        val curDate:String=day.toString() + "." + (mounth+1).toString()+"."+ year.toString()
        //сделать чтоб дважды не заполнялось

        val dbh= DbHandler(applicationContext)
        val db=dbh.getDataBase()
        val myStudentDao: StudentDao =db.myStudentDao()
        val myLessonDao:LessonDao=db.myLessonDao()
        val myAttendanceDao:AttendanceDao=db.myAttendanceDao()

        val job: Job = GlobalScope.launch(Dispatchers.IO) {
            //получаем студентов и пары
            val students=myStudentDao.getAll()

            //предполагем, что пары мы откуда-то получили
            val lessons=myLessonDao.getLessonsByDate(curDate)

            var attendance: Attendance = Attendance(0, 0, 0, true)
            for(i in lessons) {
                for(j in students){
                    attendance=Attendance(0, j.student_id, i.lesson_id, false)
                    myAttendanceDao.insert(attendance)
                }
            }
        }
        val t:TextView=findViewById(R.id.currentDate)
        t.setText(curDate)
    }
    fun changeDate(view: View){
        //запускаем календарь
        DatePickerDialog(this,this, year,mounth,day).show()
    }

    override fun onDateSet(p0: DatePicker?, year: Int, mounth: Int, dayOfMounth: Int) {
        //записываем выбранную дату
        curDate=dayOfMounth.toString()+"." + (mounth+1).toString()+"."+year.toString()
        val t:TextView=findViewById(R.id.currentDate)
        t.setText(curDate)

        createChips()
        //updateResView()
    }


}