package com.example.firstAndroid.functions.logic.network

import io.reactivex.Observable
import io.reactivex.Observer

import android.util.Log
import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import okhttp3.ResponseBody
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import com.google.gson.GsonBuilder

import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.*
import io.reactivex.schedulers.Schedulers

/**
 * https://stackoverflow.com/questions/17544751/square-retrofit-server-mock-for-testing
 */

private const val TAG = "test_network"

fun main() {
    initRxJavaPlugins()
    Log.d(TAG, "network test\n")
//    okHttpGetRequest()
//    retrofit2Request()
//    gson_test_1()
//    retrofit2RequestTemplate()
//    gson_test_2()
//    retrofit3RequestTemplate()
//    retrofit4RequestTemplate()
    retrofit2RequestRxJava()
}

private fun initRxJavaPlugins() {
    /**
     * https://stackoverflow.com/questions/66576755/io-reactivex-exceptions-undeliverableexception-the-exception-could-not-be-delive
     * The exception could not be delivered to the consumer because it has already canceled/disposed
     */
    RxJavaPlugins.setErrorHandler { e ->
        if (e is UndeliverableException) {
            Log.e(TAG, e.message ?: "ooo")
        } else {
            // Forward all others to current thread's uncaught exception handler
            Thread.currentThread().also { thread ->
                thread.uncaughtExceptionHandler.uncaughtException(thread, e)
            }
        }
    }
}

private fun okHttpGetRequest() {
    val client = OkHttpClient()
    val request = Request.Builder()
        .url("https://baidu.com")
        .build()
    client.newCall(request).enqueue(object : okhttp3.Callback {
        override fun onFailure(call: okhttp3.Call, e: IOException) {
            Log.d(TAG, "onFailure: ${e.message}")
        }

        override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
            val string = response.body?.string()
            Log.d(TAG, "正常请求 onResponse: $string")
//            val answer = JSONObject(string)
        }
    })
}

//data class Movie(var title: String, var image: String)
/**
 * https://www.cnblogs.com/qinxu/p/9504412.html
 * 属性重命名 @SerializedName 注解
 * @SerializedName(value = "emailAddress", alternate = {"email", "email_address"}): 当多种情况同时出时，以最后一个出现的值为准
 */
/**
 * https://stackoverflow.com/questions/8211304/using-enums-while-parsing-json-with-gson
 * 整形和 String 类型都是可以进行转换的
 * 不在阈值内的话会返回空
 */
enum class Status {
    @SerializedName("0")
    BUY,

    @SerializedName("1")
    DOWNLOAD,

    @SerializedName("2")
    DOWNLOADING,

    @SerializedName("3")
    OPEN
}

enum class IntStatus {
    @SerializedName("0")
    BUY,

    @SerializedName("1")
    DOWNLOAD,

    @SerializedName("2")
    DOWNLOADING,

    @SerializedName("3")
    OPEN
}

data class Movie(
    var id: Int,
    @SerializedName("custom_name") var name: String,
    var age: Int,
    var birthday: Date,
    var nullKey: String,
    @SerializedName("currentState")
    var currentState: Status? = null,
    @SerializedName("currentState2")
    var currentState2: Status? = null,
    @SerializedName("currentIntState")
    var currentIntState: IntStatus? = null,
)

class Result<T> {
    var code = 0
    var message: String? = null
    var data: T? = null
}

class BodyContent(
    var name: String?,
    var length: Int? = 2
)

data class FilesResponse(
    @SerializedName("files")
    var files: List<String>
)

interface ApiInterface {
    @GET("getIpInfo.php?ip=59.108.54.37")
    fun getIpMsg(): Call<ResponseBody?>?

    @GET("{path}/s?wd=xxx")
    fun getMovies(
        @Path("path") path: String,
        @Query("custom_query_1") query1: String
    ): Call<List<Movie>>

    @GET("{path}/s?wd=xxx")
    fun getMoviesRx(
        @Path("path") path: String,
        @Query("custom_query_1") query1: String
    ): Observable<List<Movie>>

    @GET("s?wd=yyy")
    fun getMovies2(): Call<Result<List<Movie>>>

    @FormUrlEncoded
    @POST("getIpInfo.php/wd=yyy")
    fun getMovies2Post(@Field("post_field_1") field_1: String): Call<Result<List<Movie>>>

    @POST("getIpInfo.php2/wd=yyy")
    fun getMovies4Post(
        @Body body: BodyContent,
        @Header("Location") location: String
    ): Call<Result<List<Movie>>>

    companion object {
        var BASE_URL = "https://www.baidu.com/"
        fun create(): ApiInterface {

            val client = OkHttpClient.Builder()
            client.addInterceptor(FakeInterceptor())

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(getMyGson()))
                .baseUrl(BASE_URL)
                .client(client.build())
                .build()
            return retrofit.create(ApiInterface::class.java)
        }

        fun create2(): ApiInterface {
            val client = OkHttpClient.Builder()
            client.addInterceptor(FakeInterceptor())

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(getMyGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .client(client.build())
                .build()
            return retrofit.create(ApiInterface::class.java)
        }

        /**
         * 也可以不传递参数
         */
        fun getMyGson(): Gson {
            //：内部类(Inner Class)和嵌套类(Nested Class)的区别
            val gson = GsonBuilder()
                // 序列化null 在序列化和反序化时均生效
//                .serializeNulls()
                .setDateFormat("yyyy-MM-dd")  // 设置日期时间格式，另有2个重载方法
                .disableInnerClassSerialization()  // 禁此序列化内部类
                .generateNonExecutableJson()  //生成不可执行的Json（多了 )]}' 这4个字符）
                .disableHtmlEscaping() //禁止转义html标签
                .setPrettyPrinting() //格式化输出
                .create()
            return gson
        }
    }
}

private fun retrofit2Request() {
    val apiInterface = ApiInterface.create().getMovies("my_custom_path", "query_1_value")
    apiInterface.enqueue(object : Callback<List<Movie>> {
        override fun onResponse(call: Call<List<Movie>>?, response: Response<List<Movie>>?) {
            Log.d(TAG, response?.body().toString())
        }

        override fun onFailure(call: Call<List<Movie>>?, t: Throwable?) {
            Log.d(TAG, t.toString())
        }
    })
}

private fun retrofit2RequestRxJava() {
    ApiInterface.create2().getMoviesRx("my_custom_path_2", "query_1_value")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(object : Observer<List<Movie>> {
            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "onSubscribe")
            }

            override fun onNext(t: List<Movie>) {
                Log.d(TAG, "${t.toString()}")
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, e?.toString())
            }

            /**
             * 失败不执行完成？
             */
            override fun onComplete() {
                Log.d(TAG, "complete")
            }
        })
}

private fun retrofit2RequestRxJava_Compose() {
    ApiInterface.create2().getMoviesRx("my_custom_path_2", "query_1_value")
        .compose(RxSchedulers.compose())
        .subscribe(object : Observer<List<Movie>> {
            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "onSubscribe")
            }

            override fun onNext(t: List<Movie>) {
                Log.d(TAG, "${t.toString()}")
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, e?.toString())
            }

            override fun onComplete() {
                Log.d(TAG, "complete")
            }
        })
}

private fun retrofit2RequestTemplate() {

    val apiInterface2 = ApiInterface.create().getMovies2()
    apiInterface2.enqueue(object : Callback<Result<List<Movie>>> {
        override fun onResponse(
            call: Call<Result<List<Movie>>>?,
            response: Response<Result<List<Movie>>>?
        ) {
            Log.d(TAG, response?.body().toString())
        }

        override fun onFailure(call: Call<Result<List<Movie>>>?, t: Throwable?) {
            Log.d(TAG, t.toString())
        }
    })
}

private fun retrofit3RequestTemplate() {

    val apiInterface2 = ApiInterface.create().getMovies2Post("field_1_value")
    apiInterface2.enqueue(object : Callback<Result<List<Movie>>> {
        override fun onResponse(
            call: Call<Result<List<Movie>>>?,
            response: Response<Result<List<Movie>>>?
        ) {
            Log.d(TAG, response?.body().toString())
        }

        override fun onFailure(call: Call<Result<List<Movie>>>?, t: Throwable?) {
            Log.d(TAG, t.toString())
        }
    })
}

private fun retrofit4RequestTemplate() {
    val apiInterface2 =
        ApiInterface.create().getMovies4Post(BodyContent("my_name", 3), "my_location_1")
    apiInterface2.enqueue(object : Callback<Result<List<Movie>>> {
        override fun onResponse(
            call: Call<Result<List<Movie>>>?,
            response: Response<Result<List<Movie>>>?
        ) {
            Log.d(TAG, response?.body().toString())
        }

        override fun onFailure(call: Call<Result<List<Movie>>>?, t: Throwable?) {
            Log.d(TAG, t.toString())
        }
    })
}
