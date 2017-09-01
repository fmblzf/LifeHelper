package com.fmblzf.mvp.base.inter;

/**
 * Created by Administrator on 2017/8/25.
 */

public interface IPresenter {
    /** 网络请求对应的方法 */
    public void getWebData();
    public void saveData();
    public void uploadFiles();
    public void downloadFiles();

    /** 本地缓存数据或者文件 */
    public void cache();

}
