package com.example.e_clinic

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class doctorSignup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_signup)
        //getting required reference from firebase.
        val database = Firebase.database
        val myRef = database.getReference("Doctor Data")
        //Making share pref for the finding of doctor data who sign in i pass userID from this screen to doctor main screen and search it in firebase
        var mypref1: SharedPreferences = getSharedPreferences("DoctorEM", MODE_PRIVATE)
        var editor1 = mypref1.edit()
        //required variables to fetch the data into database.
        var name: EditText = findViewById(R.id.dName)
        var qualification: EditText = findViewById(R.id.dQualification)
        var age: EditText = findViewById(R.id.dAge)
        var gender: EditText = findViewById(R.id.dGender)
        var contact: EditText = findViewById(R.id.dContact)
        var id: EditText = findViewById(R.id.dId)
        var pass: EditText = findViewById(R.id.dPassword)
        //button for signing up
        var signup: Button = findViewById(R.id.d_btnSignUp)
        signup.setOnClickListener {
            //storing the entered patient data into strings.
            var dName: String? = name.text.toString()
            var dQualification: String? = qualification.text.toString()
            var dAge: String? = age.text.toString()
            var dGender: String? = gender.text.toString()
            var dContact: String? = contact.text.toString()
            var dId: String? = id.text.toString()
            var dPass: String? = pass.text.toString()
            //creating a patient-data object, in which all our patient data required for sign up will be stored.
            var dData: DoctorData = DoctorData()
            dData.setData(dName, dQualification, dAge, dGender, dContact, dId, dPass)
            //storing the newly registered patient's data into our database with unique identification.
            myRef.child(""+dData.ID.toString()).setValue(dData).addOnCompleteListener(this@doctorSignup){
                    task ->
                if(task.isSuccessful)
                {
                    var AD = AlertDialog.Builder(this@doctorSignup)
                    AD.setTitle("Sign Up Successful")
                    AD.setPositiveButton("Continue") { dialog, which ->
                        editor1.putString("SigndocMail",dId)
                        editor1.apply()
                        editor1.commit()
                        startActivity(Intent(this@doctorSignup, doctorSign::class.java))
                        finish()
                    }
                    AD.create()
                    AD.show()
                }
                else
                {
                    var AD = AlertDialog.Builder(this@doctorSignup)
                    AD.setTitle("Sign Up Error")
                    AD.setMessage(task.exception.toString())
                    AD.setPositiveButton("Continue") { dialog, which ->
                    }
                    AD.setNegativeButton("Go Back") { dialog, which ->

                        startActivity(Intent(this@doctorSignup, doctorSign::class.java))
                        finish()
                    }
                    AD.create()
                    AD.show()
                }
            }
        }
    }
}