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

class patientSignup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_signup)
        //getting required reference from firebase.
        val database = Firebase.database
        val myRef = database.getReference("Patient Data")

        //getting authentication reference.
        var authentication: FirebaseAuth = Firebase.auth

        //creating a shared preferences to store the ID Number of patient i.e it is a unique id to identify the patient in database
        var mypref: SharedPreferences = getSharedPreferences("Patient ID", MODE_PRIVATE)
        var editor = mypref.edit()




        //required variables to fetch the data into database.
        var name: EditText = findViewById(R.id.pName)
        var DOB:EditText = findViewById(R.id.pDOB)
        var age:EditText = findViewById(R.id.pAge)
        var gender:EditText = findViewById(R.id.pGender)
        var contact:EditText = findViewById(R.id.pContactNo)
        var email:EditText = findViewById(R.id.pEmail)
        var pass:EditText = findViewById(R.id.pPass)

        //button for signing up
        var signup: Button = findViewById(R.id.btnSignUp)

        signup.setOnClickListener {

            //storing the entered patient data into strings.
            var pName: String? = name.text.toString()
            var pDOB: String? = DOB.text.toString()
            var pAge: String? = age.text.toString()
            var pGender: String? = gender.text.toString()
            var pContactNo: String? = gender.text.toString()
            var pEmail: String? = email.text.toString()
            var pPass: String? = pass.text.toString()

            //creating a patient-data object, in which all our patient data required for sign up will be stored.

            var pData: PatientData = PatientData(pName, pDOB, pAge, pGender, pContactNo, pEmail, pPass)

            //fetching the unique patient ID, it also helps us identify the number of patients registered into our DB.
            var pID = mypref.getInt("p_ID", 1)


            //here, we're checking if the user doesnt exist already then we will create new account, else user will have to try another email
            // which has not been used before.
            authentication.createUserWithEmailAndPassword(pData.email.toString(), pData.pass.toString()).addOnCompleteListener(this@patientSignup)
            {
                    task ->
                if(task.isSuccessful)
                {
                    //storing the newly registered patient's data into our database with unique identification.
                    myRef.child("Patient: " + pID).setValue(pData).addOnCompleteListener(this@patientSignup){
                            task ->

                        if(task.isSuccessful)
                        {
                            //whenever new patient registers, the patient ID will be incremented by 1 to uniquely identify it.
                            editor.putInt("p_ID", pID+1)
                            editor.apply()
                            editor.commit()
                        }
                    }

                    var AD = AlertDialog.Builder(this@patientSignup)
                    AD.setTitle("Sign Up Successful")
                    //AD.setMessage("Continue")
                    AD.setPositiveButton("Continue") { dialog, which ->

                        startActivity(Intent(this@patientSignup, patientSign::class.java))
                        finish()
                    }
                    AD.create()
                    AD.show()

                }
                else
                {
                    var AD = AlertDialog.Builder(this@patientSignup)
                    AD.setTitle("Sign Up Error")
                    AD.setMessage(task.exception.toString())
                    AD.setPositiveButton("Continue") { dialog, which ->
                    }
                    AD.setNegativeButton("Go Back") { dialog, which ->

                        startActivity(Intent(this@patientSignup, patientSign::class.java))
                        finish()
                    }
                    AD.create()
                    AD.show()
                }
            }
        }
    }
}