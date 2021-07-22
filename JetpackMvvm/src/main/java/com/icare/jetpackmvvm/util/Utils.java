package com.icare.jetpackmvvm.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.TypedValue;

import androidx.annotation.NonNull;
import androidx.core.text.TextUtilsCompat;
import androidx.core.view.ViewCompat;

import java.util.Locale;

/**
 * date: 2019/4/11
 * Utils初始化相关
 *
 * @MethodName:
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
    public static void init(@NonNull Context context) {
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
        throw new NullPointerException("u should init first");
    }
    static int toDp(Context context, int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
    }

    static boolean isRTL() {
        return TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == ViewCompat.LAYOUT_DIRECTION_RTL;
    }
}