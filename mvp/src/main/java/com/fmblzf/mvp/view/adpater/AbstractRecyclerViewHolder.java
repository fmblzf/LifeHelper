package com.fmblzf.mvp.view.adpater;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import com.fmblzf.mvp.R;

/**
 * Created by Administrator on 2017/8/28.
 */

public abstract class AbstractRecyclerViewHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    public View itemView;

    public static int createCount = 0;

    /** 缓存当前viewHolder中的所有控件 */
    private static SparseArray<View> sparseArray = new SparseArray<>();

    public AbstractRecyclerViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        this.itemView.setOnClickListener(this);
        this.itemView.setOnLongClickListener(this);
        createCount ++;
        Log.e(getClass().getSimpleName(),"创建数量："+createCount);
    }

    /**
     * 初始化Holder的所有控件显示信息
     * @param t
     */
    public abstract void initView(T t);

    /**
     * 单击事件的外部实现方法，子类重写该方法
     * @param viewHolder
     * @param position
     */
    public void onItemClick(AbstractRecyclerViewHolder viewHolder,int position){};

    /**
     * 长按事件的外部实现方法，子类重写该方法
     * @param viewHolder
     * @param position
     */
    public void onItemLongClick(AbstractRecyclerViewHolder viewHolder,int position){};

    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();
        onItemClick(this,position);
    }

    /**
     * 根据id获取对应的view,并且缓存，如果缓存中已经存在直接从缓存中获取
     * @param view 父组件
     * @param id 子组件id
     * @param <V> 子类控件类型
     * @return
     * @throws Exception
     */
    public static <V> V getViewById(View view,int id) throws Exception {
        View cacheView = sparseArray.get(id);
        if (cacheView == null){
            cacheView = view.findViewById(id);
            if (cacheView == null){
                throw new Exception("view not contain id !");
            }
//            sparseArray.append(id,cacheView);
        }
        return (V)cacheView;
    }

    @Override
    public boolean onLongClick(View v) {
        int position = getAdapterPosition();
        onItemLongClick(this,position);
        return false;
    }
}
