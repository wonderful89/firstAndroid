package com.qqz.baselib

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import java.lang.String
import kotlin.Boolean
import kotlin.Float
import kotlin.Int

class QZBaseLib {
    companion object {
        const val version = "0.0.1"
        const val tag = "QZBaseLib"

        private var currentDensityDpi: Int = 400

        fun initWithApp(appContext : Context) {
            val dm: DisplayMetrics = appContext.resources.displayMetrics
            currentDensityDpi = dm.densityDpi
        }

        fun printResolution(mContext: Context) {
            val dm: DisplayMetrics = mContext.resources.displayMetrics
            val height: Int = dm.heightPixels
            val width: Int = dm.widthPixels
            val sw: Int = mContext.resources.configuration.smallestScreenWidthDp
            Log.i("$tag Screen","屏幕分辨率: $width * $height ,densityDpi ${dm.densityDpi }, density = ${dm.density}, sw = $sw")
        }

        fun setDensityDpi(value: Int) {
            currentDensityDpi = value
        }

        /// 放大2倍
        fun magnifyTwoTimes() {
            currentDensityDpi *= 2
        }

        fun updateDensityUI(mContext: Context) {
            val value = currentDensityDpi
            val metrics  = mContext.resources.displayMetrics
//            metrics.densityDpi = value
            metrics.density = (value / 160.0).toFloat()
            metrics.scaledDensity = (value / 160.0).toFloat()
        }
    }

}