package com.example.fitnessappc

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.fitnessappc.model.UserProfile
import androidx.lifecycle.SavedStateHandle
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar

class MeViewModel(savedStateHandle: SavedStateHandle): ViewModel(){
    private val state = savedStateHandle
    private val firestore = FirebaseFirestore.getInstance()
    var calories by mutableStateOf(0)
    var protein by mutableStateOf(0)
    var carb by mutableStateOf(0)
    var fat by mutableStateOf(0)
    var userProfile by mutableStateOf<UserProfile?>(state.get<UserProfile>("userProfile"))
        private set

    fun updateUserProfile(profile: UserProfile?) {
        userProfile = profile
        state["userProfile"] = profile
    }

    init {
        fetchMacros()
    }

    fun todayAtMidnightTimestamp(): Timestamp {
        val cal = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE,      0)
            set(Calendar.SECOND,      0)
            set(Calendar.MILLISECOND, 0)
        }
        return Timestamp(cal.time)
    }

    fun fetchMacros(){
        val vm = AuthViewModel()
        val uid = vm.currentUid()
        firestore.collection("macros")
            .document(uid ?: "")
            .collection("daily")
            .document(todayAtMidnightTimestamp().toDate().toString())
            .get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    calories = snapshot.getLong("calories")?.toInt() ?: 0
                    protein = snapshot.getLong("protein")?.toInt() ?: 0
                    carb = snapshot.getLong("carb")?.toInt() ?: 0
                    fat = snapshot.getLong("fat")?.toInt() ?: 0
                }
            }
    }
}