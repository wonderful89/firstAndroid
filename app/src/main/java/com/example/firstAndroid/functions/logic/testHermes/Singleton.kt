//package com.example.firstAndroid.functions.logic.testHermes
//
//import xiaofei.library.hermes.annotation.ClassId
//import xiaofei.library.hermes.annotation.MethodId
//
//@ClassId("Singleton")
//interface ISingleton {
//
//    var value: String?
//
//    @MethodId("setValue1")
//    fun setValue1(str: String?)
//
//    @MethodId("getValue1")
//    fun getValue1():String
//}
//
//@ClassId("Singleton")
//class Singleton private constructor() {
//    private var value: String? = null
//
//    @MethodId("setValue1")
//    fun setValue1(str: String?) {
//        value = str
//    }
//
//    @MethodId("getValue1")
//    fun getValue1():String {
//        return value ?: "ddd"
//    }
//
//    companion object {
//        private var sInstance: Singleton? = null
//        val instance: Singleton?
//            get() {
//                if (sInstance == null) {
//                    synchronized(Singleton::class.java) {
//                        if (sInstance == null) {
//                            sInstance = Singleton()
//                        }
//                    }
//                }
//                return sInstance
//            }
//    }
//}