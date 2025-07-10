package com.example.fitnessappc

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import com.example.fitnessappc.BuildConfig
import org.json.JSONArray
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar


class AddFoodViewModel: ViewModel() {
    var macrosVisible by mutableStateOf(false)
    var calories by mutableStateOf(0)
    var protein by mutableStateOf(0)
    var carb by mutableStateOf(0)
    var fat by mutableStateOf(0)

    private val firestore = FirebaseFirestore.getInstance()


    fun searchFood(foodName: String, foodWeight: String){
        getMacros(foodName, foodWeight) { result, error ->
            if (error != null) {
               println(error)
            } else {
                macrosVisible = true
                val macros = result?.split(",")
                if (macros != null) {
                    println(macros)
                    calories = macros[3].trim().toInt()
                    protein = macros[0].trim().toInt()
                    carb = macros[1].trim().toInt()
                    fat = macros[2].trim().toInt()
                }
            }
        }
    }


    fun getMacros(foodName: String, foodWeight: String, callback: (result: String?, error: Exception?) -> Unit) {
        val apiKey = ""
        val client = OkHttpClient()
        println(apiKey.isBlank())
        val prompt = "Give me the macros (protein, carbs, fat and calories) for $foodWeight grams of $foodName. Just give me the NUMBERS and nothing else in this order (protein, carbs, fat and calories). remove units and labels."
        val jsonBody = JSONObject().apply {
            put("model", "gpt-3.5-turbo")
            put(
                "messages",
                JSONArray().apply {
                    put(
                        JSONObject().apply {
                            put("role", "user")
                            put("content", prompt)
                        }
                    )
                }
            )
            put("max_tokens", 10)
        }

        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = jsonBody.toString().toRequestBody(mediaType)

        val request = Request.Builder()
            .url("https://api.openai.com/v1/chat/completions")
            .post(requestBody)
            .addHeader("Authorization", "Bearer $apiKey")
            .addHeader("Content-Type", "application/json")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(null, e)
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    val bodyString = it.body?.string() ?: ""
                    if (!it.isSuccessful) {
                        callback(null, IOException("Unexpected code ${it.code}: $bodyString"))
                        return
                    }

                    try {
                        val json = JSONObject(bodyString)
                        val choices = json.getJSONArray("choices")
                        if (choices.length() > 0) {
                            val message = choices.getJSONObject(0).getJSONObject("message")
                            val text = message.getString("content")
                            callback(text, null)
                        } else {
                            callback(null, IOException("No choices in response"))
                        }
                    } catch (e: Exception) {
                        callback(null, e)
                    }
                }
            }
        })
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

    fun uploadMacros(){
        val vm = AuthViewModel()
        val uid = vm.currentUid()



        val todayId = todayAtMidnightTimestamp().toDate().toString()
        val docRef = firestore
            .collection("macros")
            .document(uid ?: "")
            .collection("daily")
            .document(todayId)

        docRef.set(
            mapOf("date" to todayAtMidnightTimestamp()),
            SetOptions.merge()
        )

        docRef.update(
            "calories", FieldValue.increment(calories.toDouble()),
            "protein", FieldValue.increment(protein.toDouble()),
            "carb",    FieldValue.increment(carb.toDouble()),
            "fat",     FieldValue.increment(fat.toDouble())
        ).addOnSuccessListener {
            println("sucess")
        }.addOnFailureListener { e ->
           println("failed")
        }
    }

}