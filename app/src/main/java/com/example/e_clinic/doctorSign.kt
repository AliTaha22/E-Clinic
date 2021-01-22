package com.example.e_clinic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class doctorSign : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_sign)


        //getting button id's
        var signup: Button = findViewById(R.id.D_signUp)
        var signIn: Button = findViewById(R.id.D_signIn)

        var signIn_email: EditText = findViewById(R.id.D_mail)
        var signIn_password: EditText = findViewById(R.id.D_Pass)

        var authentication: FirebaseAuth = Firebase.auth


        // Check if user is signed in (non-null) and update UI accordingly.
        val user = Firebase.auth.currentUser
        if(user != null)
            startActivity(Intent(this@doctorSign, doctorMainScreen::class.java))

        signIn.setOnClickListener {

                var email: String = signIn_email.text.toString()
                var pass: String = signIn_password.text.toString()
                authentication.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this@doctorSign)
                { task ->

                    //if correct username and password is entered, we will switch to main menu screen
                    if (task.isSuccessful)
                    {
                        startActivity(Intent(this@doctorSign, doctorMainScreen::class.java))
                        Toast.makeText(this, "Sign in successful", Toast.LENGTH_LONG).show()
                    }
                    else
                        Toast.makeText(this, "Error: " + task.exception.toString(), Toast.LENGTH_LONG)
                            .show()
                }

        }
        signup.setOnClickListener {
            startActivity(Intent(this, doctorSignup::class.java))
        }
    }
}