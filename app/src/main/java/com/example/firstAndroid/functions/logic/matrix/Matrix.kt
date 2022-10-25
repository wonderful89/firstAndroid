package com.example.firstAndroid.functions.logic.matrix

import android.content.Context
import com.example.firstAndroid.MyApplication
import com.tencent.matrix.Matrix
import com.tencent.matrix.iocanary.IOCanaryPlugin
import com.tencent.matrix.iocanary.config.IOConfig
import com.tencent.matrix.plugin.DefaultPluginListener
import com.tencent.matrix.report.Issue
import com.tencent.matrix.util.MatrixLog
import com.tencent.mrs.plugin.IDynamicConfig

object MatrixConfig {

    fun initMatrix() {
        val builder: Matrix.Builder = Matrix.Builder(MyApplication.instance) // build matrix

        builder.pluginListener(TestPluginListener(MyApplication.instance)) // add general pluginListener

        val dynamicConfig = DynamicConfigImplDemo() // dynamic config

        // init plugin

        // init plugin
        val ioCanaryPlugin = IOCanaryPlugin(
            IOConfig.Builder()
                .dynamicConfig(dynamicConfig)
                .build()
        )
        //add to matrix
        //add to matrix
        builder.plugin(ioCanaryPlugin)

        //init matrix

        //init matrix
        Matrix.init(builder.build())

        // start plugin

        // start plugin
        ioCanaryPlugin.start()
    }
}

class TestPluginListener(context: Context?) : DefaultPluginListener(context) {
    override fun onReportIssue(issue: Issue) {
        super.onReportIssue(issue)
        MatrixLog.e(TAG, issue.toString())

        //add your code to process data
    }

    companion object {
        const val TAG = "Matrix.TestPluginListener"
    }
}

class DynamicConfigImplDemo : IDynamicConfig {
    val isFPSEnable: Boolean
        get() = true
    val isTraceEnable: Boolean
        get() = true
    val isMatrixEnable: Boolean
        get() = true
    val isDumpHprof: Boolean
        get() = false

    override fun get(key: String, defStr: String): String {
        return defStr
    }

    override fun get(key: String, defInt: Int): Int {
        return defInt
    }

    override fun get(key: String, defLong: Long): Long {
        return defLong
    }

    override fun get(key: String, defBool: Boolean): Boolean {
        return defBool
    }

    override fun get(key: String, defFloat: Float): Float {
        return defFloat
    }
}