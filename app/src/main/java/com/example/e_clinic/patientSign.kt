package com.example.e_clinic

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
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

        var signIn_id: EditText = findViewById(R.id.P_id)
        var signIn_password:EditText = findViewById(R.id.P_Pass)

        //Making share pref for the finding of doctor data who sign in i pass id from this screen to patient main screen and search it in firebase
        var mypref1: SharedPreferences = getSharedPreferences("PatientEM", MODE_PRIVATE)
        var editor1 = mypref1.edit()
        // Check if user is signed in (non-null) and update UI accordingly.
        signIn.setOnClickListener {

            var id: String = signIn_id.text.toString()
            var pass: String = signIn_password.text.toString()
            val database = Firebase.database
            val db = database.getReference("Patient Data/")
            var use=PatientData()
            db.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (obj in snapshot.children) {
                        use = obj.getValue(PatientData::class.java)!!
                        if (id == obj.key) {
                            if (pass == use.pass)
                            {
                                editor1.putString("SignpatMail", id)
                                editor1.apply()
                                editor1.commit()
                                Toast.makeText(this@patientSign, "Sign in successfull", Toast.LENGTH_LONG).show()
                                startActivity(Intent(this@patientSign,patientMainScreen::class.java))
                                finish()
                                break
                            }
                            else
                            {
                                Toast.makeText(this@patientSign, "Invalid Password", Toast.LENGTH_SHORT).show()
                            }
                        }
                        else
                        {
                            Toast.makeText(this@patientSign, "Invalid Email", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
        signup.setOnClickListener {
            startActivity(Intent(this, patientSignup::class.java))
        }
    }
}