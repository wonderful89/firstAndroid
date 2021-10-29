package com.example.firstAndroid.functions.logic.network

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.io.OutputStreamWriter
import java.io.StringReader
import com.google.gson.annotations.SerializedName




const val tag = "test_network"
fun gson_test_1() {
    Log.d(tag, "泛型 \n")
    val gson = Gson()
    val jsonArray = "[\"Android\",\"Java\",\"PHP\"]"
    val strings = gson.fromJson(
        jsonArray,
        Array<String>::class.java
    )

    /**
     * 报错：Only classes are allowed on the left hand side of a class literal
     *
     */

    /*
    val stringList = gson.fromJson(
        jsonArray,
        List<String>::class.java
    )
    */

    var stringList: List<String?>? =
        gson.fromJson<List<String>>(jsonArray, object : TypeToken<List<String?>?>() {}.type)

    Log.d(tag, strings.toString())
    Log.d(tag, stringList.toString())
}

class User {
    //省略其它
    var name: String? = null
    var age = 0
    var emailAddress: String? = null
}

fun gson_test_2() {
    val json = "{\"name\":\"张三\",\"age\":\"24\"}"
    val user = User()
    val reader = JsonReader(StringReader(json))
    reader.beginObject()
    while (reader.hasNext()) {
        val s: String = reader.nextName()
        when (s) {
            "name" -> user.name = reader.nextString()
            "age" -> user.age = reader.nextInt() //自动转换
            "email" -> user.emailAddress = reader.nextString()
        }
    }
    reader.endObject() // throws IOException

    Log.d(tag, user.toString())
}

/**
 * //除了beginObject、endObject还有beginArray和endArray，两者可以相互嵌套，注意配对即可。beginArray后不可以调用name方法，同样beginObject后在调用value之前必须要调用name方法。
 */
fun gson_test_3() {
//    val gson = Gson()
//    val user = User("张三", 24, "zhangsan@ceshi.com")
//    gson.toJson(user, System.out)

    val writer = JsonWriter(OutputStreamWriter(System.out))
    writer.beginObject() // throws IOException
        .name("name").value("张三")
        .name("age").value(24)
        .name("email").nullValue() //演示null
        .endObject() // throws IOException
    writer.flush() // throws IOException

}