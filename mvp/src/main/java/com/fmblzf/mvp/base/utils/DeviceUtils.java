package com.fmblzf.mvp.base.utils;

import android.content.Context;

/**
 * Created by Administrator on 2017/8/28.
 */

public final class DeviceUtils {


    /**
     *
     * 1.dp和px之间的转化公式：
     *      dp * density + 0.5f = px
     *      0.5f的偏移量是为了校准数据的准确度；因为转化过程会出现小数位，但是实际使用时，使用的整形int；
     *      但是在int强制转化时，会直接去掉小数位，这就使得精度缺失较大，为了是精度更准确，加上0.5f就会实现强制转化时，四舍五入
     *
     *
     *
     */



    /**
     *
     * 将设备的dp大小转化成px大小
     * @param context
     * @param dp
     * @return
     */
    public static int convertDpToPx(Context context,int dp){
        float density = context.getResources().getDisplayMetrics().density;
        return (int)(dp * density + 0.5f);
    }

    /**
     *
     * 将设备的px大小转化成dp大小
     * @param context
     * @param px
     * @return
     */
    public static int convertPxToDp(Context context,int px){
        float density = context.getResources().getDisplayMetrics().density;
        return (int)((px - 0.5f)/density);
    }
}
