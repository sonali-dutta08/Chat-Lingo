package com.sonali.chat

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import com.sonali.chat.daos.PostDao
import kotlinx.android.synthetic.main.activity_create_post.*

class CreatePostActivity : AppCompatActivity() {

    private lateinit var postDao: PostDao
    val list = arrayListOf("English","Hindi")
    val ans: ArrayList<String> = arrayListOf()
    var lg = "Hindi"
    var z = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)
        lg = intent.getStringExtra("lang").toString()
        postDao = PostDao()


    }

//    fun getLang(str : String) : String{
//        var lang=""
//        if(str=="English"){
//            lang = TranslateLanguage.ENGLISH
//        }
//        else if(str=="Bengali"){
//            lang = TranslateLanguage.BENGALI
//        }
//        else if(str=="Hindi"){
//            lang = TranslateLanguage.HINDI
//        }
//        else if(str=="Urdu"){
//            lang = TranslateLanguage.URDU
//        }
//        else if(str=="French"){
//            lang = TranslateLanguage.FRENCH
//        }
//        else if(str=="Tamil"){
//            lang = TranslateLanguage.TAMIL
//        }
//        else if(str=="Gujarati"){
//            lang = TranslateLanguage.GUJARATI
//        }
//        return lang
//    }
//
//    fun convert(text :String,target :String){
//        val src = getLang(lg)
//        val tar = getLang(target)
//
//        if(src!=tar){
//            val options = TranslatorOptions.Builder()
//                .setSourceLanguage(src)
//                .setTargetLanguage(tar)
//                .build()
//            val englishGermanTranslator = Translation.getClient(options)
//
//            var conditions = DownloadConditions.Builder()
//                .build()
//
//
//            englishGermanTranslator.downloadModelIfNeeded(conditions)
//                .addOnSuccessListener {
//
//                    englishGermanTranslator.translate(text)
//                        .addOnSuccessListener { translatedText ->
//                            ans.add(translatedText)
//                            //Toast.makeText(this, translatedText, Toast.LENGTH_SHORT).show()
//                            if(ans.size==2){
//                                progressBar3.visibility = View.GONE
//                                if(ans.isNotEmpty()) {
//                                    postDao.addPost(ans)
//                                    finish()
//                                }
//                                z=0
//                            }
//
//                        }
//                        .addOnFailureListener { exception ->
//                            Toast.makeText(this, "ERROR in translation", Toast.LENGTH_SHORT).show()
//                        }
//                }
//                .addOnFailureListener { exception ->
//                    Toast.makeText(this, "ERROR in Downloaddddddddddddddddddddddddddddd", Toast.LENGTH_SHORT).show()
//                }
//        }
//        else{
//            ans.add(text)
//            if(ans.size==2){
//                progressBar3.visibility = View.GONE
//                if(ans.isNotEmpty()) {
//                    postDao.addPost(ans)
//                    finish()
//                }
//                z=0
//            }
//        }
//
//
//    }

    fun post(view: View) {
        progressBar3.visibility = View.VISIBLE
        val input = postInput.text.toString().trim()
        //Toast.makeText(this, input, Toast.LENGTH_SHORT).show()

//        for(str in list){
//            convert(input,str)
//        }
        if(input.isNotEmpty()) {
            progressBar3.visibility = View.GONE
            postDao.addPost(input)
            finish()
        }

    }
}