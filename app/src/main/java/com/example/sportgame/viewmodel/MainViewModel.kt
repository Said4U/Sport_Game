package com.example.sportgame.viewmodel

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainViewModel : ViewModel() {

    private  val _seconds = MutableLiveData<Long>()
    val seconds : LiveData<Long> = _seconds

    private  val _page = MutableLiveData<String>()
    val page : LiveData<String> = _page

    private val db = Firebase.firestore

    fun startTimer(){
        object : CountDownTimer(2000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                _seconds.postValue(millisUntilFinished / 1000)
            }

            override fun onFinish() {
                _seconds.postValue(0)
            }
        }.start()
    }

    fun getPage(){
        db.collection("page")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    _page.postValue(document.get("page").toString())
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Error", "Error getting documents.")
            }
    }


}