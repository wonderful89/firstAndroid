package com.example.firstAndroid.functions.ui.animation

import android.graphics.drawable.Animatable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firstAndroid.R
import kotlinx.android.synthetic.main.activity_u_i_animate2.*

class UIAnimate2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_u_i_animate2)

        startButton.setOnClickListener {
            val anim = animatedImageView.drawable as Animatable
            anim.start()
        }
    }
}
