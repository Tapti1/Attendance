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
    var listArray=students
    var listAttend=attendance
    val context=contextM

    class MyHolder(itemView: View,contextV:Context):RecyclerView.ViewHolder(itemView) {
        val studentName:TextView=itemView.findViewById(R.id.studentName)
        val studentAttend:CheckBox=itemView.findViewById(R.id.checkBox)
        val context=contextV
        val dbh= DbHandler(applicationContext)
        val db=dbh.getDataBase()
        val MyAttendanceDao: AttendanceDao =db.myAttendanceDao()

        fun setData(student: Student,attend: Attendance){
            studentName.text=student.secondName + student.firstName[0]
            studentAttend.isChecked =attend.status
            studentName.setOnClickListener{
                //переходим в студент активити
            }
            studentAttend.setOnClickListener{
                if(attend.status){
                    val newAttend:Attendance= Attendance(attend.id,attend.student_id,attend.lesson_id,false)
                    val job: Job = GlobalScope.launch(Dispatchers.IO) {
                        val students=MyStudentDao.getAll()
                        val attendance=MyAttendanceDao.getAttendancebySubjectDate(curDate,curSubject,curTimeSet)
                        runOnUiThread{
                            listAdapter.updateAdapter(students,attendance)
                        }
                    }
                }
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