package com.example.firstAndroid.functions.ui.animation

import android.graphics.drawable.Animatable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.firstAndroid.R
import com.example.firstAndroid.databinding.ActivityUIAnimate2Binding

@Route(path = "/ui/animation2")
class UIAnimate2Activity : AppCompatActivity() {

    private lateinit var binding: ActivityUIAnimate2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_u_i_animate2)
        binding = ActivityUIAnimate2Binding.inflate(layoutInflater)

        binding.startButton.setOnClickListener {
            val anim = binding.animatedImageView.drawable as Animatable
            anim.start()
        }
    }
}
