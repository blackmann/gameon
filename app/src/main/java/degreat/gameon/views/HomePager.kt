package degreat.gameon.views

import android.content.Context
import android.view.MotionEvent
import android.support.v4.view.ViewPager
import android.util.AttributeSet


class HomePager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {

    private var swipe: Boolean = false

    init {
        this.swipe = false
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (this.swipe) {
            super.onTouchEvent(event)
        } else false

    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return if (this.swipe) {
            super.onInterceptTouchEvent(event)
        } else false

    }

    fun setPagingEnabled(enabled: Boolean) {
        this.swipe = enabled
    }
}