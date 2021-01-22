package com.example.e_clinic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class doctorMainScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_main_screen)


        //getting authentication instance


        var authentication:FirebaseAuth = Firebase.auth


        //here we will get the required ids of buttons
        var dname:TextView = findViewById(R.id.D_NameET)
        var profile:Button = findViewById(R.id.D_profile)
        var viewPatients:Button = findViewById(R.id.D_viewPatients)
        var checkAppointments:Button = findViewById(R.id.D_checkAppointments)
        var signOut:Button = findViewById(R.id.D_signOut)

        //getting the name of current user whose signed in.
        val user = authentication.currentUser

        user?.let {
            //just in case if displayname is not yet loaded, we will refresh our screen so it can display the appropriate name.
            if(user?.displayName == null)
            {
                finish();
                startActivity(getIntent());
            }
           dname.text = "Hi, " + user?.displayName.toString()
        }

        profile.setOnClickListener({

            startActivity(Intent(this@doctorMainScreen, doctorProfile::class.java))

        })
        viewPatients.setOnClickListener({})
        checkAppointments.setOnClickListener({})
        signOut.setOnClickListener({

            authentication.signOut()
            startActivity(Intent(this@doctorMainScreen, doctorSign::class.java))
            finish()
        })
    }
}