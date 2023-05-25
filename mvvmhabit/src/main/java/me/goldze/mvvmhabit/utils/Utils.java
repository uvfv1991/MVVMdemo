package me.goldze.mvvmhabit.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Created by goldze on 2017/5/14.
 * 常用工具类
 */
public final class Utils {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(@NonNull final Context context) {
        Utils.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) {
            return context;
        }
        throw new NullPointerException("should be initialized in application");
    }

    /**
     * 判断列表是否为空
     *
     * @param list 列表
     * @return true表示为空，false表示不为空
     */
    public static boolean isListEmpty(List<?> list) {
        return list == null || list.size() == 0;
    }
}