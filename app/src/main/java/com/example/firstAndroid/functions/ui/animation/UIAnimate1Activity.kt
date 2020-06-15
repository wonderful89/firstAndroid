package com.example.firstAndroid.functions.ui.animation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.example.firstAndroid.R
import kotlinx.android.synthetic.main.activity_u_i_animate1.*

class UIAnimate1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_u_i_animate1)


        btn_start_animation.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(this, R.anim.scale_anim)
            animation_view.startAnimation(animation)
        }
    }
}
