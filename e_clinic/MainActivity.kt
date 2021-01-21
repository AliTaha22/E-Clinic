package com.example.e_clinic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var patientB:Button=findViewById(R.id.Patient1)
        var doctorB:Button=findViewById(R.id.Doctor1)

        doctorB.setOnClickListener {
            startActivity(Intent(this, doctorSign::class.java))
        }

        patientB.setOnClickListener {
            startActivity(Intent(this, patientSign::class.java))
        }
    }
}