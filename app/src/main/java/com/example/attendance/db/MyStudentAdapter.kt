package com.example.attendance.db

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.attendance.R
import com.example.attendance.db.DAO.AttendanceDao
import com.example.attendance.db.DAO.StudentDao
import com.example.attendance.db.tables.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MyStudentAdapter(students:ArrayList<Student>,attendance:ArrayList<Attendance>, contextM:Context):RecyclerView.Adapter<MyStudentAdapter.MyHolder> (){
    //то, что нам даётся на вход для адаптера

    var listArray=students          //список студентов
    var listAttend=attendance       //и их посещение
    val context=contextM

    class MyHolder(itemView: View,contextV:Context):RecyclerView.ViewHolder(itemView) {
        val studentName:TextView=itemView.findViewById(R.id.studentName)    //Имя в item
        val studentAttend:CheckBox=itemView.findViewById(R.id.checkBox)     //Checkbox в item
        val contextV=contextV

        //создаём DAO для работы с БД
        val dbh= DbHandler(contextV)
        val db=dbh.getDataBase()
        val MyAttendanceDao: AttendanceDao =db.myAttendanceDao()
        val MyStudentDao:StudentDao=db.myStudentDao()

        fun setData(student: Student,attend: Attendance){
            //заполняем item
            studentName.text=student.secondName + student.firstName[0]
            studentAttend.isChecked =attend.status

            studentName.setOnClickListener{
                //переходим в студент активити
            }
            studentAttend.setOnClickListener{
                //меняем посещаемость при нажатии на CheckBox

                var newAttend:Attendance= Attendance(attend.id,attend.student_id,attend.lesson_id,true)
                if(attend.status){
                    newAttend= Attendance(attend.id,attend.student_id,attend.lesson_id,false)
                }
                MyAttendanceDao.update(newAttend)
            }
        }
    }
    fun updateAdapter(newListStudent: List<Student>,newListAttend: List<Attendance>){
        listArray.clear()
        listArray.addAll(newListStudent)

        listAttend.clear()
        listAttend.addAll(newListAttend)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val inflater=LayoutInflater.from(parent.context)
        return MyHolder(inflater.inflate(R.layout.student_item,parent,false),context)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.setData(listArray.get(position),listAttend.get(position))
    }

    override fun getItemCount(): Int {
        return listArray.size
    }

}