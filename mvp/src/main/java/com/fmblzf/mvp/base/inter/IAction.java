package com.fmblzf.mvp.base.inter;

/**
 * Created by Administrator on 2017/8/25.
 */

public interface IAction<T> {
    /**
     * 数据回调
     * @param t the element signaled
     */
    public void onResponse(T t);

    /**
     * Failed terminal state.
     * @param t the throwable signaled
     */
    public void onError(Throwable t);

    /**
     * Successful terminal state.
     */
    public void onComplete();
}
