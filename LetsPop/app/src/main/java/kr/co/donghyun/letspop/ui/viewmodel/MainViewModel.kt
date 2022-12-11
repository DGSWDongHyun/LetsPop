package kr.co.donghyun.letspop.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kr.co.donghyun.letspop.data.PopularData

class MainViewModel : ViewModel() {
    private val fireStoreDB = FirebaseFirestore.getInstance()
    val popularData : MutableLiveData<PopularData> = MutableLiveData();

    init {
        getDB()
    }

    private fun getDB() {
        val docRef = fireStoreDB.collection("realtime-update-popsong").document("popularNow")

        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    popularData.postValue(document.toObject(PopularData::class.java))
                    Log.d("TAG", "tag : $popularData")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "exception : $exception")
            }
    }
}