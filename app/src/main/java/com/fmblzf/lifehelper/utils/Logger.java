package com.fmblzf.lifehelper.utils;

import android.util.Log;

import com.fmblzf.lifehelper.BuildConfig;

/**
 * 作者： fmblzf
 * 时间：2017/10/12
 * 描述：日志工具类
 */

public class Logger {
    //当前app的标记
    private static String TAG = "LifeHelper";
    //是否debug
    public static boolean isDebug = BuildConfig.DEBUG;

    public static void i(String val){
        if (isDebug){
            Log.i(TAG,val);
        }
    }

    public static void d(String val){
        if (isDebug){
            Log.d(TAG,val);
        }
    }

    public static void w(String val){
        if (isDebug){
            Log.w(TAG,val);
        }
    }

    public static void e(String val){
        if (isDebug){
            Log.e(TAG,val);
        }
    }

}
