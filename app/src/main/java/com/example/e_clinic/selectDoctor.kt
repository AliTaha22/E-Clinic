package com.example.e_clinic

import android.app.ProgressDialog
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class selectDoctor : AppCompatActivity() {

    var listenerDB: ValueEventListener?=null
    var readRef: DatabaseReference?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_doctor)

        var mypref1: SharedPreferences = getSharedPreferences("PatientEM", MODE_PRIVATE)
        var editor1 = mypref1.edit()

        var loading= ProgressDialog(this)
        loading.setTitle("Data Loading !")
        loading.setMessage("Please Wait....")
        var rc: RecyclerView =findViewById(R.id.docRecycler)
        rc.layoutManager= LinearLayoutManager(this)
        var patMail: String? =mypref1.getString("SignpatMail",null)
    //for checking of patient doctor department like MBBS Urology etc
        var patDiseas:String?=mypref1.getString("patDis",null)

        var DocName= arrayListOf<String>()
        var DocQual= arrayListOf<String>()
        var DocEmail= arrayListOf<String>()
        val database = Firebase.database
        readRef = database.getReference("Doctor Data")
        loading.show()
        listenerDB=readRef?.addValueEventListener(object : ValueEventListener {
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
                loading.dismiss()
                rc.adapter = DoctorADP(applicationContext, DocName, DocQual, DocEmail, patMail.toString())
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("listener","dd")
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
        Log.d("listener","dd")
        if (readRef != null)
        {
            if(listenerDB != null) {
                readRef?.removeEventListener(listenerDB!!)
                listenerDB=null
            }
        }
    }
}

