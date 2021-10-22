package com.example.firstAndroid.functions.test

interface TestContract {
    interface View {
        fun postDataChanged()
    }

    abstract class Presenter {
        abstract fun showAllSubjects()
        abstract fun showAllTasks(dataList: ArrayList<String>)
    }
}
