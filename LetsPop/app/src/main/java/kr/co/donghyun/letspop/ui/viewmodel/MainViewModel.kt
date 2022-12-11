package kr.co.donghyun.letspop.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kr.co.donghyun.letspop.data.PopularData

class MainViewModel : ViewModel() {
    private val fireStoreDB = FirebaseFirestore.getInstance()
    private val docRef = fireStoreDB.collection("realtime-update-popsong").document("popularNow")
    val popularData : MutableLiveData<PopularData> = MutableLiveData();

    init {
        getDB()
    }

    fun likePopSong() {
        if(popularData.value != null) {
            docRef.update(
                mapOf(
                    "like" to popularData.value!!.like ++
                )
            ).addOnSuccessListener {
                Log.d("TAG", "onSuccess")
            }
        }
    }

    fun dislikePopSong() {
        if(popularData.value != null) {
            docRef.update(
                mapOf(
                    "dislike" to popularData.value!!.dislike ++
                )
            ).addOnSuccessListener {
                Log.d("TAG", "onSuccess")
            }
        }
    }

    private fun getDB() {
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                Log.d("TAG", "$snapshot")
                popularData.postValue(snapshot.toObject<PopularData>())
            } else {
                Log.d("TAG", "update")
            }
        }
    }
}