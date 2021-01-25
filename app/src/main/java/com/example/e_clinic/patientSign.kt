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
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class patientSign : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_sign)
        //getting button id's
        var signup:Button = findViewById(R.id.P_signUp)
        var signIn:Button = findViewById(R.id.P_signIn)

        var signIn_email: EditText = findViewById(R.id.P_mail)
        var signIn_password:EditText = findViewById(R.id.P_Pass)

        var authentication: FirebaseAuth = Firebase.auth
//Making share pref for the finding of doctor data who sign in i pass email from this screen
// to patient main screen and search it in firebase
        var mypref1: SharedPreferences = getSharedPreferences("PatientEM", MODE_PRIVATE)
        var editor1 = mypref1.edit()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = authentication.currentUser
        if(currentUser != null)
            startActivity(Intent(this@patientSign, patientMainScreen::class.java))

        //here we're getting firebase-database reference
        var db = Firebase.database
        val myRef = db.getReference("Patient Data")

        signIn.setOnClickListener {

            var email: String = signIn_email.text.toString()
            var pass: String = signIn_password.text.toString()

            //first we will check whether the required slots for signing in are filled or not. if not no action will be performed
            //to prevent app from crashing.
            if(!email.isEmpty() && !pass.isEmpty())
            {

                authentication.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this)
                { task ->

                    if (task.isSuccessful) {

                        var identification: String = ""

                        myRef.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                for (obj in snapshot.children) {
                                    var user = obj.getValue(PatientData::class.java)
                                    if(currentUser?.displayName == user?.name)
                                    {
                                        identification = user?.identification.toString()
                                        Toast.makeText(this@patientSign, "hello" + identification.toString(),Toast.LENGTH_SHORT).show()
                                        break
                                    }
                                }
                            }
                            override fun onCancelled(error: DatabaseError) {
                            }
                        })

                        if(identification == "Patient")
                        {
                            editor1.putString("SignpatMail", email)
                            editor1.apply()
                            editor1.commit()
                            Toast.makeText(this, "Sign in successful", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this,patientMainScreen::class.java))
                        }
                        else if(identification != "Patient")
                        {
                            var AD = AlertDialog.Builder(this@patientSign)
                            AD.setTitle("Sign In Error")
                            AD.setTitle("Please provide Patient account")
                            AD.setPositiveButton("Continue") { dialog, which ->

                                authentication.signOut()
                                }
                        }

                    }
                    else
                        Toast.makeText(this, "Error: " + task.exception.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
        signup.setOnClickListener {
            startActivity(Intent(this, patientSignup::class.java))
        }
    }
}