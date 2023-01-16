package com.sonali.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions

import com.sonali.chat.models.Post
import kotlinx.android.synthetic.main.activity_create_post.*


class PostAdapter(options: FirestoreRecyclerOptions<Post>, val listener: IPostAdapter,val lg: String) : FirestoreRecyclerAdapter<Post, PostAdapter.PostViewHolder>(
    options
) {

    class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val postText: TextView = itemView.findViewById(R.id.postTitle)
        val userText: TextView = itemView.findViewById(R.id.userName)
        val createdAt: TextView = itemView.findViewById(R.id.createdAt)
        val likeCount: TextView = itemView.findViewById(R.id.likeCount)
        val likeButton: ImageView = itemView.findViewById(R.id.likeButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val viewHolder =  PostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false))
        viewHolder.likeButton.setOnClickListener{
            listener.onLikeClicked(snapshots.getSnapshot(viewHolder.adapterPosition).id)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int, model: Post) {

        var s = model.text

        val tar = getLang(lg)
        val src = getLang(model.createdBy.lang)

        val options = TranslatorOptions.Builder()
            .setSourceLanguage(src)
            .setTargetLanguage(tar)
            .build()
        val englishGermanTranslator = Translation.getClient(options)

//        englishGermanTranslator.translate(s)
//            .addOnSuccessListener { translatedText ->
//                s = translatedText
//                holder.postText.text = s
//
//            }
//            .addOnFailureListener { exception ->
//                println("xxx")
//            }

        var conditions = DownloadConditions.Builder()
            .build()


        englishGermanTranslator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {

                englishGermanTranslator.translate(s)
                    .addOnSuccessListener { translatedText ->
                        s = translatedText
                        holder.postText.text = s
                    }
                    .addOnFailureListener { exception ->
                        println("xyz")
                    }
            }
            .addOnFailureListener { exception ->
                println("xyz")
            }
        holder.postText.text = s
        holder.userText.text = model.createdBy.name

        holder.likeCount.text = model.likedBy.size.toString()
        holder.createdAt.text = Utils.getTimeAgo(model.createdAt)

        val auth = Firebase.auth
        val currentUserId = auth.currentUser!!.uid
        val isLiked = model.likedBy.contains(currentUserId)
        if(isLiked) {
            holder.likeButton.setImageDrawable(ContextCompat.getDrawable(holder.likeButton.context, R.drawable.ic_liked))
        } else {
            holder.likeButton.setImageDrawable(ContextCompat.getDrawable(holder.likeButton.context, R.drawable.ic_unliked))
        }

    }
}

fun getLang(str : String) : String{
    var lang=TranslateLanguage.ENGLISH
    if(str=="English"){
        lang = TranslateLanguage.ENGLISH
    }
    else if(str=="Bengali"){
        lang = TranslateLanguage.BENGALI
    }
    else if(str=="Hindi"){
        lang = TranslateLanguage.HINDI
    }
    else if(str=="Urdu"){
        lang = TranslateLanguage.URDU
    }
    else if(str=="French"){
        lang = TranslateLanguage.FRENCH
    }
    else if(str=="Tamil"){
        lang = TranslateLanguage.TAMIL
    }
    else if(str=="Gujarati"){
        lang = TranslateLanguage.GUJARATI
    }
    return lang
}

interface IPostAdapter {
    fun onLikeClicked(postId: String)
}