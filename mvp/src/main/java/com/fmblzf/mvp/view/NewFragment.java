package com.fmblzf.mvp.view;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.fmblzf.mvp.R;
import com.fmblzf.mvp.base.BaseFragment;
import com.fmblzf.mvp.base.inter.IAction;
import com.fmblzf.mvp.model.entry.NewInfo;
import com.fmblzf.mvp.presenter.impl.NewInfoPrecenter;
import com.fmblzf.mvp.view.adpater.AbstractRecyclerAdapter;
import com.fmblzf.mvp.view.adpater.AbstractRecyclerViewHolder;
import com.fmblzf.mvp.view.component.CircularImageView;

import java.util.List;

/**
 * Created by Administrator on 2017/8/25.
 */
public class NewFragment extends BaseFragment implements IProgressView {

    private static final String TAG = NewFragment.class.getSimpleName();

    public Context mContext;

    private View view;

    private RecyclerView recyclerView;

    private NewInfoPrecenter newInfoPrecenter;

    private ProgressDialog dialog ;

    private static Fragment instance;

    /**
     * 这是Fragment生命周期的起点
     * 当前Fragment和Activity开始绑定的事件
     * @param context Activity上下文内容
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDestroyView() {
        instance = null;
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 和Activity解绑，移除；这是Fragment生命周期的终点
     */
    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.news_fragment_layout,null);
        instance = this;
        return view;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        //初始化
        initWidget();
    }

    private void initWidget() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        Log.e(TAG,"mContext = "+mContext);
        dialog = new ProgressDialog(mContext);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("请稍等");

        newInfoPrecenter = new NewInfoPrecenter(this);
        newInfoPrecenter.get("top",new ResponseAction());
    }

    public class ResponseAction implements IAction<List<NewInfo>>{

        @Override
        public void onResponse(List<NewInfo> newInfos) {
            NewsAdapter newsAdapter = new NewsAdapter(mContext,newInfos);
            recyclerView.setAdapter(newsAdapter);
        }

        @Override
        public void onError(Throwable t) {

        }

        @Override
        public void onComplete() {

        }
    }

    @Override
    public void show() {
        if (dialog != null){
            dialog.show();
        }
    }

    @Override
    public void dimiss() {
        if (dialog != null){
            dialog.dismiss();
        }
    }

    public static class NewsAdapter extends AbstractRecyclerAdapter<NewInfo,NewsViewHolder>{

        public NewsAdapter(Context context, List<NewInfo> dataList) {
            super(context, dataList);
        }

        @Override
        public NewsViewHolder getViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.news_viewholder_layout,parent,false);
            NewsViewHolder viewHolder = new NewsViewHolder(view);
            return viewHolder;
        }
    }

    public static class NewsViewHolder extends AbstractRecyclerViewHolder<NewInfo>{

        public NewsViewHolder(View itemView) {
            super(itemView);
        }
        @Override
        public void initView(NewInfo newInfo){
            try {
                CircularImageView circularImageView = NewsViewHolder.getViewById(this.itemView,R.id.item_icon);
                Glide.with(instance).load(newInfo.getThumbnail_pic_s()).apply(RequestOptions.centerCropTransform()).apply(new RequestOptions().transform(new RoundedCorners(10))).into(circularImageView);
                TextView itemTitle = NewsViewHolder.getViewById(this.itemView,R.id.item_title);
                TextView subTitle = NewsViewHolder.getViewById(this.itemView,R.id.item_subtitle);
                itemTitle.setText(newInfo.getAuthor_name());
                subTitle.setText(newInfo.getTitle());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



}
