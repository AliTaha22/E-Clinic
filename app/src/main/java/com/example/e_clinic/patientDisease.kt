package com.example.e_clinic

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

class patientDisease : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_disease)
// in it we only set the disease
        var listView: ListView =findViewById(R.id.diseasList)
        var mypref1: SharedPreferences = getSharedPreferences("PatientEM", MODE_PRIVATE)
        var editor1 = mypref1.edit()
        var clist= listOf<String>("MBBS","Urology","Cardiology","Neurology","Psychiatry","Orthopaedics","E N T")
//        to use own designed layout
        var myAdapter= ArrayAdapter<String>(this, R.layout.mylayout, R.id.display ,clist)
        listView.adapter=myAdapter



        // item click listener
        listView.setOnItemClickListener{
                parent, view, position, id ->

            editor1.putString("patDis",clist[position])
            editor1.apply()
            editor1.commit()
            startActivity(Intent(this,selectDoctor::class.java))

        }


    }
}