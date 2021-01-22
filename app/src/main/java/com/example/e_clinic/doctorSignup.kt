package com.example.e_clinic

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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

        //getting authentication reference.
        var authentication: FirebaseAuth = Firebase.auth

        //creating a shared preferences to store the ID Number of Doctor i.e it is a unique id to identify the doctor in database
        var mypref: SharedPreferences = getSharedPreferences("Doctor ID", MODE_PRIVATE)
        var editor = mypref.edit()




        //required variables to fetch the data into database.
        var name: EditText = findViewById(R.id.dName)
        var qualification: EditText = findViewById(R.id.dQualification)
        var age: EditText = findViewById(R.id.dAge)
        var gender: EditText = findViewById(R.id.dGender)
        var contact: EditText = findViewById(R.id.dContact)
        var email: EditText = findViewById(R.id.dEmail)
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
            var dEmail: String? = email.text.toString()
            var dPass: String? = pass.text.toString()

            //creating a patient-data object, in which all our patient data required for sign up will be stored.

            var dData: DoctorData = DoctorData(dName, dQualification, dAge, dGender, dContact, dEmail, dPass)

            //fetching the unique doctor ID, it also helps us identify the number of doctors registered into our DB.
            var dID = mypref.getInt("d_ID", 1)


            //here, we're checking if the user(doctor) doesnt exist already then we will create new account, else user will have to try another email
            // which has not been used before.
            authentication.createUserWithEmailAndPassword(dData.email.toString(), dData.pass.toString()).addOnCompleteListener(this@doctorSignup)
            {
                    task ->
                if(task.isSuccessful)
                {
                    //storing the newly registered patient's data into our database with unique identification.
                    myRef.child("Doctor: " + dID).setValue(dData).addOnCompleteListener(this@doctorSignup){
                            task ->

                        if(task.isSuccessful)
                        {
                            //whenever new patient registers, the patient ID will be incremented by 1 to uniquely identify it.
                            editor.putInt("d_ID", dID+1)
                            editor.apply()
                            editor.commit()
                        }
                    }

                    var AD = AlertDialog.Builder(this@doctorSignup)
                    AD.setTitle("Sign Up Successful")
                    //AD.setMessage("Continue")
                    AD.setPositiveButton("Continue") { dialog, which ->

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