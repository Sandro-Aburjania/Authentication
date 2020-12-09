package com.example.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.util.Patterns
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        init()
    }

    private fun init() {
        auth = Firebase.auth
        signUp.setOnClickListener() {
            signUp()

        }
    }

    private fun signUp() {
        val email: String = emailEditText.text.toString()
        val password: String = passwordEditText.text.toString()
        val repeatPassword: String = repeatEditText.text.toString()


        if(emailEditText.text.toString().isEmpty()){
            emailEditText.error = "Please enter Email"
            emailEditText.requestFocus()
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(emailEditText.text.toString()).matches()){
            emailEditText.error =  "Please enter valid Email"
            emailEditText.requestFocus()
        }
        if(passwordEditText.text.toString().isEmpty()){
            passwordEditText.error = "Please enter Password"
            passwordEditText.requestFocus()
        }
        if(repeatEditText.text.toString().isEmpty()){
            repeatEditText.error = "Please confirm your Password"
            repeatEditText.requestFocus()
        }
        if(passwordEditText.text.toString() != repeatEditText.text.toString()){
            repeatEditText.error = "Passwords didn't match.Please try again"
            repeatEditText.requestFocus()
        }


        if (emailEditText.text.toString().isNotEmpty() && passwordEditText.text.toString()
                .isNotEmpty() && repeatEditText.text.toString().isNotEmpty()
        ) {
            if (password == repeatPassword) {
                progressBar.visibility = View.VISIBLE
                deleteClick(true)
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        progressBar.visibility = View.GONE
                        deleteClick(false)
                        if (task.isSuccessful) {
                            d("signUp", "createUserWithEmail:success")
                            Toast.makeText(this,"Sign up is Success!" , Toast.LENGTH_SHORT ).show()
                            val user = auth.currentUser
                            openAfterSignUp()
                        } else {
                            d("signUp", "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

            }
        }
    }

    private fun deleteClick(isStarted:Boolean){
            signUp.isClickable = !isStarted
        }

    private fun openAfterSignUp(){
        val intent = Intent(this, AfterSignUp::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)

    }


}




