package com.example.e_clinic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class patientMainScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_main_screen)



        var authentication: FirebaseAuth = Firebase.auth


        //here we will get the required ids of buttons
        var pName: TextView = findViewById(R.id.p_NameTV)
        var pProfile: Button = findViewById(R.id.P_profile)
        var findDoctor: Button = findViewById(R.id.P_findDoctor)
        var bookAppointment: Button = findViewById(R.id.P_bookAppointment)
        var viewReply: Button = findViewById(R.id.P_viewReply)
        var signOut: Button = findViewById(R.id.P_signOut)

        //getting the name of current user whose signed in.
        val user = authentication.currentUser

        user?.let {
            //just in case if displayname is not yet loaded, we will refresh our screen so it can display the appropriate name.
            if(user?.displayName == null)
            {
                finish();
                startActivity(getIntent());
            }
            pName.text = "Hi, " + user?.displayName.toString()
        }

        pProfile.setOnClickListener({

            startActivity(Intent(this@patientMainScreen, patientProfile::class.java))
        })
        findDoctor.setOnClickListener({})
        bookAppointment.setOnClickListener({})
        viewReply.setOnClickListener({})
        signOut.setOnClickListener({

            authentication.signOut()
            startActivity(Intent(this@patientMainScreen, patientSign::class.java))
            finish()

        })




    }//on create ends here
}