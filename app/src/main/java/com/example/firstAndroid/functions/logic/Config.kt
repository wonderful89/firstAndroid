package com.example.firstAndroid.functions.logic

enum class LogicTest(val title: String, val content: String = "", val path: String = "") {
    Util("工具测试22", path = "/logic/path"),
    Network(" 网络测试", path = "/logic/network"),
    Storage("存储测试",path = "/logic/storage"),
    Reflect("反射测试",path = "/logic/reflect"),
    DownloadManager("下载测试",path = "/logic/download"),
    ReactiveX("ReactiveX", path = "/logic/reactivex"),
    OtherPureFunction("OtherPureFunction", path = "/logic/pureFunction"),
}