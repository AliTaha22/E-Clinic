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

class doctorSign : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_sign)


        //getting button id's
        var signup: Button = findViewById(R.id.D_signUp)
        var signIn: Button = findViewById(R.id.D_signIn)
        var signIn_ID: EditText = findViewById(R.id.D_id)
        var signIn_password: EditText = findViewById(R.id.D_Pass)
        //Making share pref for the finding of doctor data who sign in i pass id from this screen to doctor main screen and search it in firebase
        var mypref1: SharedPreferences = getSharedPreferences("DoctorEM", MODE_PRIVATE)
        var editor1 = mypref1.edit()


        signIn.setOnClickListener {

            var id: String = signIn_ID.text.toString()
            var pass: String = signIn_password.text.toString()
            val database = Firebase.database
            val db = database.getReference("Doctor Data/")
            var use=DoctorData()
            db.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (obj in snapshot.children) {
                        use = obj.getValue(DoctorData::class.java)!!
                        if (id == obj.key) {
                            if (pass == use.pass) {
                                editor1.putString("SigndocMail", id)
                                editor1.apply()
                                editor1.commit()
                                startActivity(Intent(this@doctorSign, doctorMainScreen::class.java))
                                Toast.makeText(this@doctorSign, "Sign in successful", Toast.LENGTH_LONG).show()
                                finish()
                            } else {
                                Toast.makeText(this@doctorSign, "Invalid Password", Toast.LENGTH_SHORT).show()
                            }
                        }
                        else
                        {
                            Toast.makeText(this@doctorSign, "Invalid User-ID", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
        signup.setOnClickListener {
            startActivity(Intent(this, doctorSignup::class.java))
        }
    }
}