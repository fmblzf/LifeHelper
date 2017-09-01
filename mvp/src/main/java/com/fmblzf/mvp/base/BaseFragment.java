package com.fmblzf.mvp.base;

import android.app.Fragment;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.fmblzf.mvp.base.inter.IPresenter;
import com.fmblzf.mvp.base.inter.IViewModel;

/**
 * Created by fmblzf on 2017/8/25.
 *
 * 1.获取数据
 * 2.上传数据
 * 3.设置数据
 * 4.提取数据
 * 5.获取UI
 *
 *
 *
 *
 */

@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public abstract class BaseFragment<P extends IPresenter,V extends IViewModel> extends Fragment {

    private P presenter;
    private V viewModel;






}
