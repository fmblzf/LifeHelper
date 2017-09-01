package com.fmblzf.mvp.view.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/8/28.
 */

public abstract class AbstractRecyclerAdapter<T,V extends AbstractRecyclerViewHolder<T>> extends RecyclerView.Adapter<V> {
    private List<T> list;
    private Context mContent;
    private LayoutInflater inflater;

    public AbstractRecyclerAdapter(Context context,List<T> dataList){
        this.list = dataList;
        this.mContent = context;
        this.inflater = LayoutInflater.from(context);
    }

    public abstract V getViewHolder(LayoutInflater inflater,ViewGroup parent, int viewType);

    @Override
    public V onCreateViewHolder(ViewGroup parent, int viewType) {
        return getViewHolder(inflater,parent,viewType);
    }

    @Override
    public void onBindViewHolder(V holder, int position) {
        T t = list.get(position);
        holder.initView(t);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
}
