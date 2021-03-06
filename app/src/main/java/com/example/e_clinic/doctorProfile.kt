package com.example.e_clinic

import android.app.ProgressDialog
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class doctorProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_profile)
        var textName:TextView=findViewById(R.id.DP_Name)
        var textQual:TextView=findViewById(R.id.DP_Qual)
        var textAge:TextView=findViewById(R.id.DP_Age)
        var textGender:TextView=findViewById(R.id.DP_Gender)
        var textContact:TextView=findViewById(R.id.DP_Contact)
        var textMail:TextView=findViewById(R.id.DP_Email)

        var loading= ProgressDialog(this)
        loading.setTitle("Data Loading !")
        loading.setMessage("Please Wait....")

        var mypref1: SharedPreferences = getSharedPreferences("DoctorEM", MODE_PRIVATE)
        var editor1 = mypref1.edit()
        var signerMail=mypref1.getString("SigndocMail",null)
        val database = Firebase.database
        val db = database.getReference("Doctor Data/")
        var user=DoctorData()
        loading.show()
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (obj in snapshot.children) {
                    user = obj.getValue(DoctorData::class.java)!!
                    if(signerMail==user?.ID)
                    {
                        textName.text =  user?.name.toString()
                        textQual.text =  user?.qualification.toString()
                        textAge.text =  user?.age.toString()
                        textGender.text =  user?.gender.toString()
                        textContact.text =  user?.contact.toString()
                        textMail.text =  user?.ID.toString()
                        break
                    }
                }
                loading.dismiss()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}