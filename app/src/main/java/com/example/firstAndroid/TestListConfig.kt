package com.example.firstAndroid

enum class MainTest(val title: String, val content: String = "") {
    UI("UI测试", content = "用于UI的工具测试"),
    Util("工具测试"),
    Logic("逻辑测试"),
    Component("组件测试"),
    DensitySetting("设置density放大2被(生效-其他activity生效)")
}