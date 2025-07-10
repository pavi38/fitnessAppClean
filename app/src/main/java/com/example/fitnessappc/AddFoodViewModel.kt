package com.example.fitnessappc

import androidx.lifecycle.ViewModel
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import org.json.JSONArray
import java.io.IOException
import com.example.fitnessappc.BuildConfig

class AddFoodViewModel: ViewModel() {

    private val MAX_RETRIES = 3

    fun searchFood(foodName: String, foodWeight: String){
        getMacros(foodName, foodWeight) { result, error ->
            if (error != null) {
               println(error)
            } else {
                println(result)
            }
        }
    }

    private fun enqueueWithRetry(
        client: OkHttpClient,
        request: Request,
        attempt: Int,
        callback: (result: String?, error: Exception?) -> Unit
    ) {
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(null, e)
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    val bodyString = it.body?.string() ?: ""

                    // Retry on rate limiting
                    if (it.code == 429 && attempt < MAX_RETRIES) {
                        val retryAfter = it.header("Retry-After")?.toLongOrNull() ?: 1L
                        Thread.sleep(retryAfter * 1000)
                        enqueueWithRetry(client, request, attempt + 1, callback)
                        return
                    }

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


    fun getMacros(foodName: String, foodWeight: String, callback: (result: String?, error: Exception?) -> Unit) {
        val apiKey = BuildConfig.API_KEY
        if (apiKey.isBlank()) {
            callback(null, IllegalStateException("API key is missing. Set API_KEY in your .env file"))
            return
        }
        val client = OkHttpClient()

        val prompt = "Give me the macros (protein, carbs, fat and calories) for $foodWeight grams of $foodName"
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

        enqueueWithRetry(client, request, 1, callback)
    }

}