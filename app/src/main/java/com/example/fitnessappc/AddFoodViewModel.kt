package com.example.fitnessappc

import androidx.lifecycle.ViewModel
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import com.example.fitnessappc.BuildConfig

class AddFoodViewModel: ViewModel() {

    fun searchFood(foodName: String, foodWeight: String){
        getMacros(foodName, foodWeight) { result, error ->
            if (error != null) {
               println(error)
            } else {
                println(result)
            }
        }
    }


    fun getMacros(foodName: String, foodWeight: String, callback: (result: String?, error: Exception?) -> Unit) {
        val apiKey = BuildConfig.API_KEY
        val client = OkHttpClient()

        val prompt = "tell me a joke"
        val jsonBody = JSONObject().apply {
            put("model", "gpt-3.5-turbo")
            put("prompt", prompt)
            put("max_tokens", 10)
        }

        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = jsonBody.toString().toRequestBody(mediaType)

        val request = Request.Builder()
            .url("https://api.openai.com/v1/chat/completions")
            .post(requestBody)
            .addHeader("Authorization", "Bearer $apiKey")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(null, e)
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!it.isSuccessful) {
                        callback(null, IOException("Unexpected code $response"))
                        return
                    }

                    try {
                        val bodyString = it.body?.string() ?: ""
                        val json = JSONObject(bodyString)
                        val choices = json.getJSONArray("choices")
                        if (choices.length() > 0) {
                            val text = choices.getJSONObject(0).getString("text")
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

}