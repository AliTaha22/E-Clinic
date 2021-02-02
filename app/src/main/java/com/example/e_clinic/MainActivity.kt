package com.example.e_clinic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        ActivityCompat.requestPermissions(this,
            arrayOf(android.Manifest.permission.SEND_SMS),
            101)
        var patientB:Button=findViewById(R.id.Patient1)
        var doctorB:Button=findViewById(R.id.Doctor1)

        doctorB.setOnClickListener {
            startActivity(Intent(this, doctorSign::class.java))
        }

        patientB.setOnClickListener {
            startActivity(Intent(this, patientSign::class.java))
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}