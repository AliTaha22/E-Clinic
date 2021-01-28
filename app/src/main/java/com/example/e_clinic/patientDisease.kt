package com.example.e_clinic

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

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
        var signerId=mypref1.getString("SignpatMail",null)



        // item click listener
        //in it i set the feild of medical select by user and we use it in doctor portal
        listView.setOnItemClickListener{
                parent, view, position, id ->

            editor1.putString("patDis",clist[position])
            editor1.apply()
            editor1.commit()
            val database = Firebase.database
            val myRef = database.getReference("Patient Data")
            myRef.child(signerId.toString()).child("dep").setValue(clist[position])
            startActivity(Intent(this,selectDoctor::class.java))

        }


    }
}