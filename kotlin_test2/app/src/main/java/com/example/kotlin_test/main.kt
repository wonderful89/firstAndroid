package com.example.kotlin_test

fun main() {
    val b = run loop@{
        listOf(1, 2, 3, 4, 5).forEach {
            if (it == 3) return@loop // 从传入 run 的 lambda 表达式非局部返回
            print(it)
        }
    }
    print(" done with nested loop: $b")
}

/**
 *
 */
fun String.test(): String {
    return "test"
}