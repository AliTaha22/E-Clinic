package com.example.e_clinic

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class selectPatient : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_patient)


        var mypref1: SharedPreferences = getSharedPreferences("DoctorEM", MODE_PRIVATE)
        var editor1 = mypref1.edit()

        var rc: RecyclerView =findViewById(R.id.patRecycler)
        rc.layoutManager= LinearLayoutManager(this)
        var docId: String? =mypref1.getString("SigndocMail",null)
        var docQual:String?=mypref1.getString("docQual",null)

        var PatName= arrayListOf<String>()
        var PatId= arrayListOf<String>()
        var PatAge= arrayListOf<String>()
        var PatGender= arrayListOf<String>()

        val database = Firebase.database
        val db = database.getReference("Patient Data")
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (obj in snapshot.children) {
                    var us: PatientData? = obj.getValue(PatientData::class.java)
                    if (docQual == us?.dep) {
                        PatName.add((us?.name).toString())
                        PatId.add((us?.ID).toString())
                        PatAge.add((us?.ID).toString())
                        PatGender.add((us?.ID).toString())
                    }
                }
                rc.adapter =
                    PatientADP(applicationContext, PatName, PatId, docId.toString())
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}