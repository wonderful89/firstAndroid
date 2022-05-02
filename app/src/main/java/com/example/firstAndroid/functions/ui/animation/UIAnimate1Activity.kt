package com.example.firstAndroid.functions.ui.animation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.firstAndroid.R
import com.example.firstAndroid.databinding.ActivityUIAnimate1Binding

//import kotlinx.android.synthetic.main.activity_u_i_animate1.*

@Route(path = "/ui/animation1")
class UIAnimate1Activity : AppCompatActivity() {

    private lateinit var binding: ActivityUIAnimate1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_u_i_animate1)
        binding = ActivityUIAnimate1Binding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnStartAnimation.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(this, R.anim.scale_anim)
            binding.animationView.startAnimation(animation)
        }
    }
}
