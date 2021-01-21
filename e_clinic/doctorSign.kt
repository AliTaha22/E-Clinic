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



        signIn.setOnClickListener({

            var email:String = signIn_email.text.toString()
            var pass:String = signIn_password.text.toString()
            authentication.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this)
            {
                    task ->

                if(task.isSuccessful)
                    Toast.makeText(this, "Sign in successfull", Toast.LENGTH_LONG).show()
                else
                    Toast.makeText(this, "Error: "+ task.exception.toString() , Toast.LENGTH_LONG).show()
            }


        })
        signup.setOnClickListener({
            startActivity(Intent(this,doctorSignup::class.java))
        })
    }
}