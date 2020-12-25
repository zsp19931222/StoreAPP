package me.andy.mvvmhabit.binding.viewadapter.radiusview;

import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

import me.andy.mvvmhabit.util.ZLog;
import me.andy.mvvmhabit.view.shape.RadiusRelativeLayout;
import me.andy.mvvmhabit.view.shape.RadiusTextView;
import me.andy.mvvmhabit.view.shape.delegate.RadiusTextViewDelegate;

/**
 * description:
 * author:created by Andy on 2019/7/25 0025 11:49
 * email:zsp872126510@gmail.com
 */
public class ViewAdapter {
    @BindingAdapter(value = {"rv_backgroundColor","rv_backgroundPressedColor"}, requireAll = false)
    public static void RadiusTextViewBg(View view, int rv_backgroundColor,int rv_backgroundPressedColor) {
        if (view instanceof RadiusTextView) {
            ((RadiusTextView) view).getDelegate().setBackgroundColor(ContextCompat.getColor( view.getContext(), rv_backgroundColor)).init();
        }else if (view instanceof RadiusRelativeLayout){
            try {
                ((RadiusRelativeLayout) view).getDelegate().setBackgroundColor(ContextCompat.getColor( view.getContext(), rv_backgroundColor)).init();
                ((RadiusRelativeLayout) view).getDelegate().setBackgroundPressedColor(ContextCompat.getColor( view.getContext(), rv_backgroundPressedColor)).init();
            }catch (Exception e){
                ((RadiusRelativeLayout) view).getDelegate().setBackgroundColor(rv_backgroundColor).init();
                ((RadiusRelativeLayout) view).getDelegate().setBackgroundPressedColor(rv_backgroundPressedColor).init();
            }
        }
    }

    @BindingAdapter(value = {"rv_rippleEnable", "rv_rippleColor"}, requireAll = false)
    public static void RadiusTextViewRipple(RadiusTextView view, boolean rv_rippleEnable, int rv_rippleColor) {
        ZLog.d(rv_rippleEnable);
        ZLog.d(rv_rippleColor);
        RadiusTextViewDelegate radiusTextViewDelegate = view.getDelegate();
        radiusTextViewDelegate.setRippleEnable(rv_rippleEnable);
        radiusTextViewDelegate.setRippleColor(ContextCompat.getColor(view.getContext(), rv_rippleColor));
        radiusTextViewDelegate.init();
    }
}
