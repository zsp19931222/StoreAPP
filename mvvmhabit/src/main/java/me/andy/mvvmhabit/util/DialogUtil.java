package me.andy.mvvmhabit.util;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

import me.andy.mvvmhabit.base.BaseApplication;


/**
 * description:
 * author:created by Andy on 2019/8/16 0016 16:50
 * email:zsp872126510@gmail.com
 */
public class DialogUtil {
    private MaterialDialog dialog;

//    private LoadingPopup dialog;
    private static final DialogUtil ourInstance = new DialogUtil();

    public static DialogUtil getInstance() {
        return ourInstance;
    }

    private DialogUtil() {
    }

    public void showDialog(Context context, String title) {
        try {
            if (dialog != null) {
                dialog.show();
            } else {
                MaterialDialog.Builder builder = MaterialDialogUtils.showIndeterminateProgressDialog(context, title, true);
                dialog = builder.show();
            }
        } catch (Exception e) {
            ZLog.d(e.toString());
        }
    }

    public void dismissDialog() {
        ZLog.d(dialog);
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
