package com.example.firstAndroid.base.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.GsonBuilder


val gson = Gson()
/**
 * 将 Any 对象转换为 json 字符串
 */
fun Any.toJson(): String {
    return gson.toJson(this)
}

/**
 *  将 json 字符串装换为 对应的对象
 */
inline fun <reified T> String.fromJson(): T? {
    val type = object : TypeToken<T>() {}.type
    return gson.fromJson(this, type)
}

/**
 * 根据 模型字段的版本，将 jsonStr 转换为对应的模型
 */
inline fun <reified T> String.fromJsonV(version: Double): T? {
    val gsonV = GsonBuilder().setVersion(version).create()
    val type = object : TypeToken<T>() {}.type
    return gsonV.fromJson(this, type)
}

fun Any.toJsonV(version: Double): String {
    val gsonV = GsonBuilder().setVersion(version).create()
    return gsonV.toJson(this)
}
