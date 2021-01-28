package com.example.e_clinic

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class selectDoctor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_doctor)

        var mypref1: SharedPreferences = getSharedPreferences("PatientEM", MODE_PRIVATE)
        var editor1 = mypref1.edit()

        var rc: RecyclerView =findViewById(R.id.docRecycler)
        rc.layoutManager= LinearLayoutManager(this)
        var patMail: String? =mypref1.getString("SignpatMail",null)
        var patDiseas:String?=mypref1.getString("patDis",null)

        var DocName= arrayListOf<String>()
        var DocQual= arrayListOf<String>()
        var DocEmail= arrayListOf<String>()

        val database = Firebase.database
        val db = database.getReference("Doctor Data")
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (obj in snapshot.children) {
                    var us: DoctorData? = obj.getValue(DoctorData::class.java)
                    if(patDiseas==us?.qualification)
                    {
                        DocName.add((us?.name).toString())
                        DocQual.add((us?.qualification).toString())
                        DocEmail.add((us?.ID).toString())
                    }
                }
                rc.adapter = DoctorADP(applicationContext, DocName, DocQual, DocEmail,patDiseas.toString(),patMail.toString())
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })


    }
}

