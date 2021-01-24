package com.example.e_clinic

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class patientProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_profile)

        var textName: TextView =findViewById(R.id.PP_Name)
        var textQual: TextView =findViewById(R.id.PP_DOB)
        var textAge: TextView =findViewById(R.id.PP_Age)
        var textGender: TextView =findViewById(R.id.PP_Gender)
        var textContact: TextView =findViewById(R.id.PP_Contact)
        var textMail: TextView =findViewById(R.id.PP_Email)
        var textDisease: TextView =findViewById(R.id.PP_Disease)

        //Getting data from firebase
        var mypref1: SharedPreferences = getSharedPreferences("PatientEM", MODE_PRIVATE)
        var editor1 = mypref1.edit()
        var signerMail=mypref1.getString("SignpatMail",null)
        val database = Firebase.database
        val db = database.getReference("Patient Data/")
        var user=PatientData()
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (obj in snapshot.children) {
                    user = obj.getValue(PatientData::class.java)!!
                    if(signerMail==user?.email)
                    {
                        textName.text =  user?.name.toString()
                        textQual.text =  user?.DOB.toString()
                        textAge.text =  user?.age.toString()
                        textGender.text =  user?.gender.toString()
                        textContact.text =  user?.contact.toString()
                        textMail.text =  user?.email.toString()
                        textDisease.text=user?.disease.toString()
                        break
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}