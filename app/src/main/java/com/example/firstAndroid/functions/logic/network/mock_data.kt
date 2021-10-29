package com.example.firstAndroid.functions.logic.network

import android.util.Log
import com.example.firstAndroid.BuildConfig
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONObject
import java.io.IOException
import java.net.URI
import com.google.gson.Gson

class FakeInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var response: Response? = null
        Log.w("test_network", "test env = " + BuildConfig.DEBUG)
        if (BuildConfig.DEBUG) {
            // Get Request URI.
            val uri: URI = chain.request().url.toUri()
            // Get Query String.
            val query: String? = uri.getQuery()
            // Parse the Query String.
            val parsedQuery = query?.split("=".toRegex())?.toTypedArray() ?: arrayOf()
//            val responseString = "[{\"id\":1,\"age\":28,\"name\":\"Victor Apoyan\"}]"
            var responseString: String = ""
            responseString = when {
                parsedQuery.contains("xxx") -> mockList.toJSONString()
                parsedQuery.contains("yyy") -> mockListResponse.toJSONString()
                else -> item0.toJSONString()
            }
            val body = chain.request().body
            val headers = chain.request().headers
            Log.w("test_network", responseString)
            Log.w("test_network", "body = ${body.toString()}\n")
            Log.w("test_network", "header = ${headers.toString()}\n")

            val mediaType = "application/json".toMediaTypeOrNull()
            var data = responseString.toByteArray()
            response = Response.Builder()
                .code(200)
                .message(responseString)
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .body(data.toResponseBody(mediaType))
                .addHeader("content-type", "application/json")
                .build()
        } else {
            response = chain.proceed(chain.request())
        }
        return response
    }

    companion object {
        var item0 = mapOf<String, Any>(
            "id" to 1,
            "age" to 30,
            "custom_name" to "item0",
            "birthday" to "2021-10-10",
            "currentState" to "2",
            "currentState2" to "12",
            "currentIntState" to 3,
        )
        var mockList = listOf<Map<String, Any>>(item0)
        var mockListResponse =
            mapOf<String, Any>("code" to 400, "message" to "success", "data" to mockList)
    }
}

fun List<Any>.toJSONString(): String {
    // JSONArray jsonArray = new JSONArray(myList);
    return Gson().toJson(this)
}

fun Map<String, Any>.toJSONString(): String {
    return Gson().toJson(this)
//    var jsonObject = JSONObject(this)
//    val jsonObj = JSONObject()
//    for ((key, value) in this) {
//        jsonObj.put(key, value)
//    }
//    jsonObj.toString()
}