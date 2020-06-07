package com.example.firstAndroid.functions.ui.touchMe

import java.util.*

class Dot(var x: Float, var y: Float, var color: Int, var diameter: Int) {}

@ExperimentalUnsignedTypes
fun Dots.onDotsChanged(continuation: (dots: Dots) -> Unit) {
    setDotsChangeListener(object : Dots.DotsChangeListener {
        override fun onDotsChange(dots: Dots) {
            continuation(dots)
        }
    })
}

@ExperimentalUnsignedTypes
private fun Dots.setListeners2(helper: DotsChangeListenerHelper) {
    this.onDotsChanged {
        helper.onDotsChange(it)
    }
}

@ExperimentalUnsignedTypes
fun Dots.setListeners(init: DotsChangeListenerHelper.() -> Unit) {
    val listener = DotsChangeListenerHelper()
    listener.init()
    this.setListeners2(listener)
}

@ExperimentalUnsignedTypes
private typealias CCListener = (Dots) -> Unit

// 本质上还是 Dots.DotsChangeListener 这个接口的实现类。只是使用方法上面简单些
@ExperimentalUnsignedTypes
class DotsChangeListenerHelper: Dots.DotsChangeListener{
    private var mDotsChange: CCListener? = null
    private var mDotsChangeNew: CCListener? = null

    fun  onDotsChangeXX(dotsChange: CCListener){
        mDotsChange = dotsChange
    }

    fun  onDotsChangeNewXX(dotsChangeNew: CCListener){
        mDotsChangeNew = dotsChangeNew
    }

    override fun onDotsChange(dots: Dots) {
        mDotsChange?.invoke(dots)
    }

    override fun onDotsChangeNew(dots: Dots) {
        mDotsChangeNew?.invoke(dots)
    }
}

@ExperimentalUnsignedTypes
class Dots {

    interface DotsChangeListener {
        fun onDotsChange(dots: Dots);
        fun onDotsChangeNew(dots: Dots) = Unit;
    }

    private var dots = LinkedList<Dot>()
    private val safeDots = Collections.unmodifiableList(dots)
    private var dotsChangeListener: DotsChangeListener? = null
    private var dotsChangeListener2: ((dots: Dots) -> Unit)? = null;

    fun setDotsChangeListener(listen: DotsChangeListener) {
        dotsChangeListener = listen
    }

    fun setDotsChangeListener2(listen: ((dots: Dots) -> Unit)) {
        dotsChangeListener2 = listen
    }

    fun getLastDot(): Dot? {
        return dots.last
    }

    fun getDots(): MutableList<Dot> {
        return Collections.unmodifiableList(dots)
    }

    fun addDot(x: Float, y: Float, color: Int, diameter: Int) {
        dots.add(
            Dot(
                x,
                y,
                color,
                diameter
            )
        )
        notifyListener()
    }

    fun clearDots() {
        dots.clear()
        notifyListener()
    }

    private fun notifyListener() {
        dotsChangeListener?.onDotsChange(this)
        dotsChangeListener2?.invoke(this)
    }

}