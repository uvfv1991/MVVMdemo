package com.example.agroapp.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.example.agroapp.app.AppApplication;

/**
 * author : jiangxue
 * date : 2023/5/25 13:53
 * description :
 */
public class Util {

    public static final int[] getWindowMetrics(Context mContext) {
        int[] datas = new int[2];

        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        datas[0] = dm.widthPixels;
        datas[1] = dm.heightPixels;
        return datas;
    }
    public static final int dpTopx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, Float.valueOf(dp), AppApplication.getInstances().getResources().getDisplayMetrics());
    }
}
