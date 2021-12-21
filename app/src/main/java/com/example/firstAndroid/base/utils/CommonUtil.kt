package com.example.firstAndroid.base.utils

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.*
import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.Utils
import com.example.firstAndroid.MyApplication
import com.example.firstAndroid.R
import org.jetbrains.anko.textColor
import org.jetbrains.anko.wrapContent
import java.io.File
import java.util.*
import android.view.ViewGroup

private var lastClickTime: Long = 0

object CommonUtils {

    // 两次点击间隔不能少于 1000ms
    var FAST_CLICK_DELAY_TIME = 1000

    /**
     * 取消 快速点击的检测
     */
    fun resetFastInValid() {
        FAST_CLICK_DELAY_TIME = 0
    }

    fun resetValid() {
        FAST_CLICK_DELAY_TIME = 1000
    }

}

/***
 * 是否是快速点击
 */
@JvmOverloads
fun isFastClick(
    delayTime: Int = CommonUtils.FAST_CLICK_DELAY_TIME,
    resetLastTime: Boolean = true
): Boolean {
    var flag = true
    val currentClickTime = System.currentTimeMillis()
    if (currentClickTime - lastClickTime >= delayTime) {
        flag = false
    }
    if (resetLastTime) {
        lastClickTime = currentClickTime
    }
    return flag
}

/***
 * 是否是快速点击
 */
@JvmOverloads
fun isFastClick2(delayTime: Int = CommonUtils.FAST_CLICK_DELAY_TIME): Boolean {
    var flag = true
    val currentClickTime = System.currentTimeMillis()
    if (currentClickTime - lastClickTime >= delayTime) {
        lastClickTime = currentClickTime
        flag = false
    }
    return flag
}

/**
 * 显示 Toast
 */
fun String.showToast() {
    if (this.isNotEmpty()) {
        ToastUtil.showShort(MyApplication.instance!!, this)
    }
}

fun showToast(@StringRes resId: Int, @StringRes enResId: Int = 0) {
    val str = getString(resId, enResId)
    if (str.isNotEmpty()) {
        ToastUtil.showShort(MyApplication.instance!!, str)
    }
}

/**
 * 显示 LongToast
 */
fun String.showLongToast() {
    ToastUtil.showLong(MyApplication.instance!!, this)
}

fun isEn(): Boolean {
    val locale: Locale = Utils.getApp().resources.configuration.locale
    val language: String = locale.language
    return language.toLowerCase().endsWith("en")
}

/**
 * 通过 id 获取字符串
 *  中文 ： resId
 *  英文：
 *      enRes -> enString -> resId
 *
 *
 *
 */
@JvmOverloads
fun getString(
    @StringRes resId: Int,
    @StringRes enResId: Int = 0,
    enString: String? = null
): String {
    return if (isEn()) {
        when {
            enResId != 0 -> {
                Utils.getApp().getString(enResId)
            }
            enString != null -> {
                enString
            }
            else -> {
                Utils.getApp().getString(resId)
            }
        }
    } else {
        Utils.getApp().getString(resId)
    }

}

@NonNull
fun getDimension(@DimenRes dimensionId: Int) = Utils.getApp().resources.getDimension(dimensionId)

@NonNull
fun getDimensionPixelSize(@DimenRes dimensionId: Int) =
    Utils.getApp().resources.getDimensionPixelSize(dimensionId)

fun getDrawable(@DrawableRes drawableId: Int) =
    Utils.getApp().resources.getDrawable(drawableId, null)

fun getColor2(@ColorRes colorInt: Int) = Utils.getApp().resources.getColor(colorInt)

fun File.sendFileChangeBroadcast() {
    val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
    intent.data = Uri.fromFile(this) // 需要更新的文件路径
    Utils.getApp().sendBroadcast(intent)
}

fun String.toMD5() = EncryptUtils.encryptMD5ToString(this) ?: ""

fun String.utf8Length() = this.toByteArray(Charsets.UTF_8).size

/**
 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
 */
fun dp2px(context: Context, dpValue: Float): Int {
    val scale = context.resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

/**
 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
 */
fun px2dp(context: Context, pxValue: Float): Int {
    val scale = context.resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}

fun Context.inflate(
    resource: Int,
    root: ViewGroup? = null,
    attachToRoot: Boolean = (root != null)
): View =
    LayoutInflater.from(this).inflate(resource, root, attachToRoot)

class ToastUtil {
    companion object {
        private var TAG = this.javaClass.simpleName
        private var mToast: Toast? = null
        private var mHandler = Handler(Looper.getMainLooper())

        fun showShort(context: Context, content: String) {
            show(context, content, Toast.LENGTH_SHORT)
        }

        fun showLong(context: Context, content: String) {
            show(context, content, Toast.LENGTH_LONG)
        }

        /**
        <TextView
        android:id="@+id/tvContent"
        android:layout_width="@dimen/dp360"
        android:layout_height="@dimen/dp180"
        android:layout_gravity="center"

        android:padding="12dp"
        android:gravity="center"
        android:text="Toast"
        android:textColor="@color/colorBlackOp4"
        android:textSize="21sp" />
         */
        private const val tvContentId = 39894
        private fun getToastView(context: Context): LinearLayout {
            val linearLayout = LinearLayout(context)
            linearLayout.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            val textView = CustomTextView(context)
            linearLayout.addView(textView)

            textView.layoutParams.width = getDimensionPixelSize(R.dimen.dp360)
            textView.layoutParams.height = getDimensionPixelSize(R.dimen.dp80)
            val padding = getDimensionPixelSize(R.dimen.dp10)
            textView.setPadding(padding, padding, padding, padding)
            textView.gravity = Gravity.CENTER
            textView.textColor = 0x7F000000
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21f)
            textView.id = tvContentId

            return linearLayout
        }

        private fun show(context: Context, content: String, duration: Int) {
            mHandler.post {
//                val inflater =
//                    context.applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//                val view = inflater.inflate(R.layout.toast_layout, null)
//                val tvContent = view.findViewById(R.id.tvContent) as TextView
                val view = getToastView(context)
                val tvContent = view.findViewById(tvContentId) as TextView
                tvContent.text = content

                //实例化toast
                if (mToast == null) {
                    mToast = Toast(context.applicationContext)
                }
                mToast?.view = view
                mToast?.duration = duration
                mToast?.setGravity(Gravity.CENTER, 0, 0)
                try {
                    mToast?.show()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}

class CustomTextView(context: Context,
                  attrs: AttributeSet? = null,
                  defStyleAttr: Int = 0,): androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
        width = getDimensionPixelSize(R.dimen.dp2)
        typeface = Typeface.create("", Typeface.BOLD)
    }
    private var hasCanvas = false
    override fun onDraw(canvas: Canvas?) {
        if (!hasCanvas) {
            hasCanvas = true
            paint.color = Color.WHITE
            paint.style = Paint.Style.FILL
            canvas!!.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
            paint.color = Color.BLACK
            paint.style = Paint.Style.STROKE
            canvas!!.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
        }
        super.onDraw(canvas)
    }
}