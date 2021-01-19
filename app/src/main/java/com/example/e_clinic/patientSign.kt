package com.example.e_clinic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class patientSign : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_sign)
        var signup:Button=findViewById(R.id.P_signUp)



        signup.setOnClickListener({
            startActivity(Intent(this,patientSignup::class.java))
        })
    }
}