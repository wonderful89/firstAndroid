package com.example.firstAndroid.functions.logic.reactivex

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.firstAndroid.R
import com.example.firstAndroid.functions.logic.storage.StorageMainActivity
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

@Route(path = "/logic/reactivex")
class MainReactiveActivity : AppCompatActivity() {

    companion object {
        const val tag = "MainReactiveActivity"
    }

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_reactive)

        getObservable().subscribe(getStringObserver())

        getObservable().subscribe {  Log.d(tag, "$it 调试1") }

        getObservable().subscribe( Consumer ({
            Log.d(tag, "$it 调试2")
        }))

        getObservable().subscribe( Consumer {
            Log.d(tag, "$it 调试3")
        })

        var a: (String, String) -> Int = { input, input2 -> Log.d(tag, "$input $input2 调试4")}
        a("1111", "2222")
        getObservable().subscribe()
    }

    private fun getObservable(): Observable<String> {
        return Observable.just("1", "2", "3", "4", "5")
    }

    private fun getStringObserver(): Observer<String> {
        return object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                Log.d(tag, "onSubscribe")
            }

            override fun onNext(t: String) {
                Log.d(tag, "onNext: $t")
            }

            override fun onError(e: Throwable) {
                Log.e(tag, "onError: " + e.message)
            }

            override fun onComplete() {
                Log.d(tag, "onComplete")
            }
        }
    }

}