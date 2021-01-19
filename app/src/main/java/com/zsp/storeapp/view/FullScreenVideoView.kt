package com.zsp.storeapp.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.VideoView
import me.andy.mvvmhabit.util.ZLog

class FullScreenVideoView : VideoView {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
    }

//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        val width = View.getDefaultSize(0, widthMeasureSpec)
//        val height = View.getDefaultSize(0, heightMeasureSpec)
//        ZLog.d(width)
//        ZLog.d(height)
//        setMeasuredDimension(width, height)
//    }
}