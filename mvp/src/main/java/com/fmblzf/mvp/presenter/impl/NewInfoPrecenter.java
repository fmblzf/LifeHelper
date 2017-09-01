package com.fmblzf.mvp.presenter.impl;

import android.util.Log;

import com.fmblzf.mvp.base.inter.IAction;
import com.fmblzf.mvp.model.entry.NewInfo;
import com.fmblzf.mvp.model.service.INewService;
import com.fmblzf.mvp.presenter.INewInfoPrecenter;
import com.fmblzf.mvp.view.IProgressView;
import com.fmblzf.network.retrofit.RetrofitUtils;
import com.fmblzf.network.rxjava.FlowableManager;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/8/25.
 */

public class NewInfoPrecenter {

    private static final String TAG = NewInfoPrecenter.class.getSimpleName();
    /** 聚合数据接口平台的请求url */
    private static final String BASE_URL = "http://v.juhe.cn/";
    /** 聚合数据接口平台申请的key */
    private static final String JUHE_KEY = "dd7bba438f17877f6790d49087424418";

    private IProgressView progressView;
    private boolean isShowProgress = false;

    public NewInfoPrecenter(IProgressView iview){
        this.progressView = iview;
        this.isShowProgress = true;

    }

    public NewInfoPrecenter(){
        this.isShowProgress = false;
    }

    public void setIsShowProgress(boolean isshow){
        this.isShowProgress = isshow;
    }

    public void get(String type, IAction<List<NewInfo>> action){
        showProgress();
        INewService service = RetrofitUtils.getInstance().createService(BASE_URL, GsonConverterFactory.create() ,INewService.class);
        Flowable<JsonObject> jsonFlowable = service.getNew(type,JUHE_KEY);
        handlerResponse(jsonFlowable,action);
    }
    public void post(String type, IAction<List<NewInfo>> action){
        showProgress();
        INewService service = RetrofitUtils.getInstance().createService(BASE_URL, GsonConverterFactory.create() ,INewService.class);
        Flowable<JsonObject> jsonFlowable = service.postNew(type,JUHE_KEY);
        handlerResponse(jsonFlowable,action);
    }

    private void showProgress(){
        if (isShowProgress && progressView != null){
            progressView.show();
        }
    }

    private void dismiss(){
        if (progressView != null){
            progressView.dimiss();
        }
    }

    private void handlerResponse(Flowable<JsonObject> jsonFlowable,final IAction<List<NewInfo>> action){
        FlowableManager.convertType(jsonFlowable,new JsonConvertClass()).subscribe(new Subscriber<List<NewInfo>>() {
            @Override
            public void onSubscribe(Subscription s) {
                Log.d(TAG,"onSubscribe");
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(List<NewInfo> newInfos) {
                Log.d(TAG,"onNext=="+newInfos.toString());
                if (action != null){
                    action.onResponse(newInfos);
                }
            }

            @Override
            public void onError(Throwable t) {
                Log.d(TAG,"onError");
                if (action != null){
                    action.onError(t);
                }
                dismiss();
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"onComplete");
                if (action != null){
                    action.onComplete();
                }
                dismiss();
            }
        });
    }

    private static class JsonConvertClass implements FlowableManager.IConvertDataType<JsonObject,List<NewInfo>>{

        @Override
        public List<NewInfo> convert(JsonObject jsonObject) {
            Log.d(TAG,jsonObject.toString());
            JsonElement result = jsonObject.getAsJsonObject("result").get("data");
            Gson gson = new Gson();
            List<NewInfo> list = gson.fromJson(result,new TypeToken<List<NewInfo>>(){}.getType());
            Log.d(TAG,"list=="+list.toString());
            return list;
        }
    }



}
