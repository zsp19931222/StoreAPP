package me.andy.mvvmhabit.view.shape;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import me.andy.mvvmhabit.R;
import me.andy.mvvmhabit.util.SizeUtils;
import me.andy.mvvmhabit.util.ZLog;
import me.andy.mvvmhabit.view.shape.delegate.RadiusViewDelegate;


/**
 * Created: AriesHoo on 2017-02-10 14:24
 * E-Mail: AriesHoo@126.com
 * Function:用于需要圆角矩形框背景的RelativeLayout的情况,减少直接使用RelativeLayout时引入的shape资源文件
 * Description:
 */
public class RadiusRelativeLayoutDefaultImage extends RelativeLayout {

    private RadiusViewDelegate delegate;
    float defaultImageW;
    public RadiusRelativeLayoutDefaultImage(Context context) {
        this(context, null);
    }

    public RadiusRelativeLayoutDefaultImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray mTypedArray=context.obtainStyledAttributes(attrs, R.styleable.RadiusRelativeLayoutDefaultImage);
        defaultImageW = mTypedArray.getDimension(R.styleable.RadiusRelativeLayoutDefaultImage_rv_defaultImageW, SizeUtils.dp2px(24));
        mTypedArray.recycle();
        delegate = new RadiusViewDelegate(this, context, attrs);
    }

    /**
     * 获取代理类用于Java代码控制shape属性
     *
     * @return
     */
    public RadiusViewDelegate getDelegate() {
        return delegate;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (delegate != null && delegate.getWidthHeightEqualEnable() && getWidth() > 0 && getHeight() > 0) {
            int max = Math.max(getWidth(), getHeight());
            int measureSpec = MeasureSpec.makeMeasureSpec(max, MeasureSpec.EXACTLY);
            super.onMeasure(measureSpec, measureSpec);
            return;
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (delegate != null) {
            if (delegate.getRadiusHalfHeightEnable()) {
                delegate.setRadius(getHeight() / 2);
            }
            delegate.init();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //设置占位、出错图
        int w = (int) defaultImageW;
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.default_icon_image);
        canvas.drawBitmap(bmp, null, new Rect((canvas.getWidth() - w) / 2, (canvas.getHeight() - w) / 2, (canvas.getWidth() - w) / 2 + w, (canvas.getHeight() - w) / 2 + w), null);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (delegate != null)
            delegate.setSelected(selected);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (delegate != null) {
            delegate.init();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return false;
    }
}
