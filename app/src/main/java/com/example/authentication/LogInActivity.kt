package com.example.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_log_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class LogInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        init()
    }

    private fun init() {
        auth = Firebase.auth
        logIn.setOnClickListener() {
            logIn()

        }
    }

    private fun logIn() {
        val email: String = emailSignIn.text.toString()
        val password: String = passwordSignIn.text.toString()

        if (emailSignIn.text.toString().isEmpty()) {
            emailSignIn.error = "Please enter Email"
            emailSignIn.requestFocus()
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(emailSignIn.text.toString()).matches()){
            emailSignIn.error =  "Please enter valid Email"
            emailSignIn.requestFocus()
        }
        if (passwordSignIn.text.toString().isEmpty()) {
            passwordSignIn.error = "Please enter Password"
            passwordSignIn.requestFocus()
        }


        if (emailSignIn.text.toString().isNotEmpty() && passwordSignIn.text.toString()
                .isNotEmpty()
        ) {
            logInProgressbar.visibility = View.VISIBLE

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    logInProgressbar.visibility = View.GONE
                    if (task.isSuccessful) {
                        d("logIN", "signInWithEmail:success")
                        Toast.makeText(this, "Authentication is Success!", Toast.LENGTH_SHORT)
                            .show()
                        val user = auth.currentUser
                        openAfterLogIn()
                    } else {
                        d("logIn", "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

    }


    private fun openAfterLogIn() {
        val intent = Intent(this, AfterLogIn::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)

    }


}


