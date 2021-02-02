package com.example.e_clinic

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class doctorMainScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_main_screen)

        //here we will get the required ids of buttons
        var dname:TextView = findViewById(R.id.D_NameET)
        var profile:Button = findViewById(R.id.D_profile)
        var viewPatients:Button = findViewById(R.id.D_viewPatients)
        var checkAppointments:Button = findViewById(R.id.D_checkAppointments)
        var signOut:Button = findViewById(R.id.D_signOut)






//==========================================Data Reading of the Doctor who sign in====================
        //Share pref for searching Sign in doctor data from fire base by the help of id
        var mypref1: SharedPreferences = getSharedPreferences("DoctorEM", MODE_PRIVATE)
        var editor1 = mypref1.edit()
        var signerId=mypref1.getString("SigndocMail",null)
        val database = Firebase.database
        val db = database.getReference("Doctor Data/")
        var user=DoctorData()
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (obj in snapshot.children) {
                    user = obj.getValue(DoctorData::class.java)!!
                    if(signerId==user?.ID)
                    {
                        dname.text = "Hi, Dr " + user?.name.toString()
                        editor1.putString("docQual",user?.qualification.toString())
                        editor1.apply()
                        editor1.commit()
                        break
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
//==========================================Data Reading of the Doctor who sign in====================








        profile.setOnClickListener({

            startActivity(Intent(this@doctorMainScreen, doctorProfile::class.java))

        })




        viewPatients.setOnClickListener({
            startActivity(Intent(this,selectPatient::class.java))
        })
        checkAppointments.setOnClickListener({})


        signOut.setOnClickListener({

            editor1.putString("SigndocMail",null)
            editor1.putString("docQual",null)
            editor1.apply()
            editor1.commit()
            startActivity(Intent(this@doctorMainScreen, doctorSign::class.java))
            finish()
        })
    }
}