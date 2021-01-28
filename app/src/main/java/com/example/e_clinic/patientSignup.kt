package com.example.e_clinic

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class patientSignup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_signup)
        //getting required reference from firebase.
        val database = Firebase.database
        val myRef = database.getReference("Patient Data")

        //Making share pref for the finding of Patient data who sign in i pass email from this screen to patient main screen and search it in firebase
        var mypref1: SharedPreferences = getSharedPreferences("PatientEM", MODE_PRIVATE)
        var editor1 = mypref1.edit()
        //Progress bar syntax
        var loading= ProgressDialog(this)
        loading.setTitle("Signing !")
        loading.setMessage("Please Wait....")
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

            loading.show()
            //storing the entered patient data into strings.
            var pName: String? = name.text.toString()
            var pDOB: String? = DOB.text.toString()
            var pAge: String? = age.text.toString()
            var pGender: String? = gender.text.toString()
            var pContactNo: String? = contact.text.toString()
            var pEmail: String? = email.text.toString()
            var pPass: String? = pass.text.toString()
            //creating a patient-data object, in which all our patient data required for sign up will be stored.
            var pData: PatientData = PatientData()
            pData.setData(pName, pDOB, pAge, pGender, pContactNo, pEmail, pPass)
            //here, we're checking if the user doesnt exist already then we will create new account, else user will have to try another email
            // which has not been used before.
            //storing the newly registered patient's data into our database with unique identification.
            myRef.child("" +pData.ID.toString()).setValue(pData).addOnCompleteListener(this@patientSignup){
                task ->
                if(task.isSuccessful)
                {
                    loading.dismiss()
                    var AD = AlertDialog.Builder(this@patientSignup)
                    AD.setTitle("Sign Up Successful")
                    AD.setPositiveButton("Continue") { dialog, which ->
                        editor1.putString("SignpatMail", pEmail)
                        editor1.apply()
                        editor1.commit()
                        startActivity(Intent(this@patientSignup, patientSign::class.java))
                        finish()
                    }
                    AD.create()
                    AD.show()
                }
                else
                {
                    loading.dismiss()
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
                    Toast.makeText(baseContext, " "+task.exception.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}