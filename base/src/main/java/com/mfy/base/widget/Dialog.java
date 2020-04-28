package com.mfy.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.shenghui.base.R;

public class Dialog {

    /**
     * 底部显示对话框
     * @param context
     * @param layoutId  布局ID
     * @param overLay   时候显示dialog背景阴影
     * @return
     */
    public static View showBottomDialog(Context context,int layoutId,boolean overLay){
        View v = LayoutInflater.from(context).inflate(layoutId, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetDialog);
        bottomSheetDialog.setContentView(v);
        bottomSheetDialog.show();
        if (overLay){
            bottomSheetDialog.getWindow().setDimAmount(0f);
        }
        return v;
    }


    public static View showDialog(Context context,int layoutId){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View inflate = LayoutInflater.from(context).inflate(
                layoutId, null, false);
        builder.setView(inflate);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return inflate;
    }

}
