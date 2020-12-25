package me.andy.mvvmhabit.util;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import me.andy.mvvmhabit.R;
import me.andy.mvvmhabit.loading.AVLoadingIndicatorView;
import razerdp.basepopup.BasePopupWindow;

/**
 * description:
 * author:created by Andy on 2020/6/2 0002 17:24
 * email:zsp872126510@gmail.com
 */
public class LoadingPopup extends BasePopupWindow {
    LoadingPopup(Context context, String tips) {
        super(context);
        setOutSideDismiss(false);
        setBackgroundColor(Color.parseColor("#00000000"));
        AVLoadingIndicatorView loadingIndicatorView = findViewById(R.id.indicator);
        TextView textView = findViewById(R.id.tips);
        textView.setText(tips);
        loadingIndicatorView.show();
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_loading);
    }

}
