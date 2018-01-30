package com.example.tr.myapplication.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.tr.myapplication.R;

import java.io.UnsupportedEncodingException;


/**
 * Toast统一管理类
 */
public class To {


    private static Toast mToast;
    private static Toast mLongToast;
    private static View v;


    private To() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }


    /**
     * 短时间显示Toast
     *
     * @param context
     * @param
     */
    @SuppressLint("ShowToast")
    public static void ShowToast(Context context, String msg) {
        if (mToast == null){
            mToast = Toast.makeText(context.getApplicationContext(),msg,Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();



    }


    /**
     * 长时间显示Toast
     *
     * @param context
     * @param
     */
    public static void ShowLongToast(Context context, String msg) {
        if (mLongToast == null){
            mLongToast = Toast.makeText(context.getApplicationContext(),msg,Toast.LENGTH_LONG);
        }
        mLongToast.setText(msg);
        mLongToast.setGravity(Gravity.CENTER, 0, 0);
        mLongToast.show();
    }

    public static String toUtf8(String str) {
        String result = null;
        try {
            result = new String(str.getBytes("UTF-8"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 创建自定义ProgressDialog
     *
     * @param context
     * @return
     */
    public static Dialog createLoadingDialog(Context context) {

        LayoutInflater inflater = LayoutInflater.from(context.getApplicationContext());
        View v = inflater.inflate(R.layout.layout_loading_dialog, null); // 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view); // 加载布局
        // 创建自定义样式dialog
        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);
        loadingDialog.setCancelable(true);
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        return loadingDialog;
    }


    public static Dialog createLoadingDialog(Context context, String message) {
        LayoutInflater inflater = LayoutInflater.from(context.getApplicationContext());
        View v = inflater.inflate(R.layout.layout_loading_dialog, null); // 得到加载view
        ((TextView) v.findViewById(R.id.loadi)).setText(message);
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view); // 加载布局
        // 创建自定义样式dialog

        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);
        loadingDialog.setCancelable(true);
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        return loadingDialog;

    }

}