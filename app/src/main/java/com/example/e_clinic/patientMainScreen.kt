package com.example.e_clinic

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class patientMainScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_main_screen)




        //here we will get the required ids of buttons
        var pName: TextView = findViewById(R.id.p_NameTV)
        var pProfile: Button = findViewById(R.id.P_profile)
        var findDoctor: Button = findViewById(R.id.P_findDoctor)
        var bookAppointment: Button = findViewById(R.id.P_bookAppointment)
        var viewReply: Button = findViewById(R.id.P_viewReply)
        var signOut: Button = findViewById(R.id.P_signOut)


        //==========================================Data Reading of the Patient who sign in====================
        //Share pref for searching Sign in Patient data from fire base by the help of id
        var mypref1: SharedPreferences = getSharedPreferences("PatientEM", MODE_PRIVATE)
        var editor1 = mypref1.edit()
        var signerMail=mypref1.getString("SignpatMail",null)
        val database = Firebase.database
        val db = database.getReference("Patient Data/")
        var use=PatientData()
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (obj in snapshot.children) {
                    use = obj.getValue(PatientData::class.java)!!
                    if(signerMail==use?.ID)
                    {
                        pName.text = "Hi, Mr/Mrs " + use?.name.toString()
                        break
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
//==========================================Data Reading of the Patient who sign in====================

        pProfile.setOnClickListener {

            startActivity(Intent(this@patientMainScreen, patientProfile::class.java))
        }
        findDoctor.setOnClickListener {
            startActivity(Intent(this,patientDisease::class.java))
        }
        signOut.setOnClickListener {
            editor1.putString("SignpatMail",null)
            editor1.putString("patDis",null)
            editor1.apply()
            editor1.commit()
            startActivity(Intent(this@patientMainScreen, patientSign::class.java))
            finish()
        }


    }//on create ends here
}