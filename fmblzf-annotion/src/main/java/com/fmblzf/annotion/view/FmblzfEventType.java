package com.fmblzf.annotion.view;

/**
 * 作者： fmblzf
 * 时间：2017/9/1
 */

public enum FmblzfEventType {

    /**
     *  单击事件
     */
    CLICK(0),
    /**
     * 长按事件
     */
    LONG_CLICK(1),
    /**
     * 双击事件
     */
    DOUBLE_CLICK(2);

    private int value;

    private FmblzfEventType(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
