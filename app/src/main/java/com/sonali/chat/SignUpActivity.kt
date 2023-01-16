package com.sonali.chat

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.sonali.chat.databinding.ActivitySignUpBinding
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        firebaseAuth = FirebaseAuth.getInstance()
    }

//    override fun onStart() {
//        super.onStart()
//        val currentUser = firebaseAuth.currentUser
//        if(currentUser != null) {
//            Log.e(TAG, "onStart: ")
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
//    }

    fun Login(view: View) {
        progressBar2.visibility = View.VISIBLE
        val mail = binding.language.text.toString()
        val pass = binding.password.text.toString()
        val pass2 = binding.password2.text.toString()


        if(mail.isNotEmpty() && pass.isNotEmpty() && pass2.isNotEmpty()){
            if(pass == pass2){
                firebaseAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener {
                    if(it.isSuccessful){
                        val intent = Intent(this,SignInActivity::class.java)
                        progressBar2.visibility = View.GONE
                        startActivity(intent)
                    }
                    else{
                        progressBar2.visibility = View.GONE
                        Toast.makeText(this,"Invalid Credentials", Toast.LENGTH_LONG).show()
                    }
                }
            }
            else{
                progressBar2.visibility = View.GONE
                Toast.makeText(this,"Passwords don't match", Toast.LENGTH_LONG).show()
            }
        }
        else{
            progressBar2.visibility = View.GONE
            Toast.makeText(this,"Fields can't be empty!!", Toast.LENGTH_LONG).show()
        }

    }

    fun SignIn(view: View) {
        val intent = Intent(this,SignInActivity::class.java)
        startActivity(intent)
    }
}