package com.example.firstAndroid.functions.test

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.firstAndroid.base.BaseActivity

import javax.inject.Inject

@Route(path = "/task/test")
class TestActivity1 : BaseActivity(), TestContract.View {

    override fun isDagger(): Boolean = true

    @Inject
    lateinit var mPresenter: TestContract.Presenter

//    override fun getLayoutResId() = R.layout.activity_composite_paper2

    override fun onCreating(savedInstanceState: Bundle?) {
//        mPresenter.takeView(this)
        Log.d("", "$mPresenter")
    }

    override fun postDataChanged() {
        Log.d("", "postDataChanged")
    }
}

