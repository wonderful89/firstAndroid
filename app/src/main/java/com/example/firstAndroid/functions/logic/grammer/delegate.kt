package com.example.firstAndroid.functions.logic.grammer

import kotlin.properties.Delegates
import kotlin.properties.Delegates.observable
import kotlin.reflect.KProperty

/**
 * https://www.kotlincn.net/docs/reference/delegated-properties.html
 */
class Delegate {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef, thank you for delegating '${property.name}' to me!"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$value has been assigned to '${property.name}' in $thisRef.")
    }
}

// var test1 : String= "1111"

interface MyProtocol{
    fun print(str: String)
}

class MyCustomClass constructor(var name: String?, var age: Int?, var example: Example? = null) : MyProtocol{
    fun printName() {
        println("name = $name")
    }

    override fun print(str: String) {
        println("$str: print example in MyCustomClass: $example")
    }
}

class MyCustomClassDelegate{
    operator fun getValue(example: Example, property: KProperty<*>): MyCustomClass {
        println("example = $example in MyCustomClassDelegate")
        return MyCustomClass("name", 10, example)
    }

    operator fun setValue(example: Example, property: KProperty<*>, myCustomClass: MyCustomClass) {

    }
}

/**
 * 这样就可以实现代理类型和主要类型的分离
 */
class Example {
    var p: String by Delegate()

    val customClass: MyCustomClass by MyCustomClassDelegate()

    val customClass2 = MyCustomClass("", 10, this)

    var newName: Int = 0
    @Deprecated("Use 'newName' instead", ReplaceWith("newName"))
    var oldName: Int by this::newName

//    var pro: MyProtocol by DelegateProtocol()

    var name: String by Delegates.observable("<no name>") {
            prop, old, new ->
        println("$old -> $new")
    }

    var name2: String by Delegates.vetoable("<no name2>") {
        a, old, new ->
        println("$old -> $new")
        new != "No"
    }

    val lazyValue: String by lazy {
        println("computed!")
        "Hello"
    }
}

fun main() {
    println("in main")
    var e = Example()
    println(" e = ${e.p}")
    println(" e = ${e.customClass.printName()}")
    e.customClass.print("111")
    e.customClass2.print("222")

    e.oldName = 42
    println("e.newName = ${e.newName}")

    e.name = "firstName"
    e.name = "lastName"

    e.name2 = "firstName"
    println("1: ${e.name2}")
    e.name2 = "No"
    println("2: ${e.name2}")
    e.name2 = "lastName"
    println("3: ${e.name2}")

    println(e.lazyValue)
    println(e.lazyValue)
}