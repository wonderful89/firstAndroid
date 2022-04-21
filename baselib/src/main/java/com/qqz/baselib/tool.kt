package com.qqz.baselib

import android.app.Activity
import androidx.core.content.ContextCompat.startActivity

class QZBaseTool {
    companion object {
        /**
         * recreate() 界面效果不好
         * https://stackoverflow.com/questions/2486934/programmatically-relaunch-recreate-an-activity
         */
        fun recreateActivity(activity: Activity, isFirstRoute: Boolean = false) {
            if (isFirstRoute) {
                activity.recreate()
            } else {
                activity.startActivity(activity.intent)
                activity.finish()
                activity.overridePendingTransition(0, 0)
            }
        }
    }
}