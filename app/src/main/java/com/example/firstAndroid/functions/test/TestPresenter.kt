package com.example.firstAndroid.functions.test


import android.util.Log
import javax.inject.Inject

class TestPresenter @Inject
constructor() : TestContract.Presenter() {
    override fun showAllSubjects() {
        Log.d("", "showAllSubject")
    }

    override fun showAllTasks(dataList: ArrayList<String>) {
        Log.d("", "showAllTasks")
    }

}

