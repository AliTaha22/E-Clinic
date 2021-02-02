package com.example.e_clinic

import android.app.ProgressDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class selectPatient : AppCompatActivity() {
    var listenerDB: ValueEventListener?=null
    var readRef: DatabaseReference?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_patient)



        var mypref1: SharedPreferences = getSharedPreferences("DoctorEM", MODE_PRIVATE)
        var editor1 = mypref1.edit()

        var rc: RecyclerView =findViewById(R.id.patRecycler)
        rc.layoutManager= LinearLayoutManager(this)
        var docId: String? =mypref1.getString("SigndocMail", null)
        var docQual:String?=mypref1.getString("docQual", null)

        var PatName= arrayListOf<String>()
        var PatId= arrayListOf<String>()
        var PatAge= arrayListOf<String>()
        var PatGender= arrayListOf<String>()
        var Patcontact= arrayListOf<String>()


        var loading= ProgressDialog(this)
        loading.setTitle("Data Loading !")
        loading.setMessage("Please Wait....")

        val database = Firebase.database
        readRef = database.getReference("Patient Data")
        loading.show()
        listenerDB=readRef?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (obj in snapshot.children) {
                    var us: PatientData? = obj.getValue(PatientData::class.java)
                    if (docQual == us?.dep) {
                        PatName.add((us?.name).toString())
                        PatId.add((us?.ID).toString())
                        PatAge.add((us?.age).toString())
                        PatGender.add((us?.gender).toString())
                        Patcontact.add((us?.contact).toString())
                    }
                }
                loading.dismiss()
                rc.adapter = PatientADP(applicationContext, PatName, PatId,PatAge,PatGender,Patcontact, docId.toString())
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        if (readRef != null)
        {
            if(listenerDB != null) {
                readRef?.removeEventListener(listenerDB!!)
                listenerDB=null
            }
        }
    }
    override fun onPause() {
        super.onPause()
        if (readRef != null) {
            if (listenerDB != null) {
                readRef?.removeEventListener(listenerDB!!)
                listenerDB = null
            }
        }
    }
}