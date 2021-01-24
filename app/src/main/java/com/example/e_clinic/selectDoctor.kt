package com.example.e_clinic

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class selectDoctor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_doctor)
/*
        var mypref1: SharedPreferences = getSharedPreferences("PatientEM", MODE_PRIVATE)
        var editor1 = mypref1.edit()

        var patMail: String? =mypref1.getString("SignpatMail",null)
        var patDiseas:String?=mypref1.getString("patDis",null)


        val database = Firebase.database
        val db = database.getReference("users/profile/")
        var user: DoctorData? =DoctorData()
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (obj in snapshot.children) {
                    var us: DoctorData? = obj.getValue(DoctorData::class.java)
                    if(patDiseas==us?.qualification)
                    {
                        user=us
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
*/

    }
}