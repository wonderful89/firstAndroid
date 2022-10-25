package com.example.firstAndroid.functions.logic.matrix

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.firstAndroid.R

@Route(path = "/logic/matrix")
class MatrixTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matrix_test)
        MatrixConfig.initMatrix()
    }
}