package com.sonali.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.sonali.chat.daos.UserDao
import com.sonali.chat.databinding.ActivitySignInBinding
import com.sonali.chat.databinding.ActivitySignUpBinding
import com.sonali.chat.models.User
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        firebaseAuth = FirebaseAuth.getInstance()
    }

    fun SignIn(view: View) {
        progressBar.visibility = View.VISIBLE
        val name = binding.username.text.toString()
        val lang = binding.language.text.toString()
        val mail = binding.email.text.toString()
        val pass = binding.password.text.toString()



        if(mail.isNotEmpty() && pass.isNotEmpty() && lang.isNotEmpty()){
            firebaseAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener {
                if(it.isSuccessful){
                    val intent = Intent(this,MainActivity::class.java)
                    val currentUser = firebaseAuth.currentUser
                    if(currentUser!=null){
                        val user = User(currentUser.uid,name,lang)
                        val usersDao = UserDao()
                        usersDao.addUser(user)
                    }
                    intent.putExtra("LANG",lang)
                    progressBar.visibility = View.GONE
                    startActivity(intent)
                }
                else{
                    progressBar.visibility = View.GONE
                    Toast.makeText(this,"Invalid Credentials", Toast.LENGTH_LONG).show()
                }
            }
        }
        else{
            progressBar.visibility = View.GONE
            Toast.makeText(this,"Fields can't be empty!!", Toast.LENGTH_LONG).show()
        }

    }
}