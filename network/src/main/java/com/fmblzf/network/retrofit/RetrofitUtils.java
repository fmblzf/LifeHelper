package com.fmblzf.network.retrofit;

import com.fmblzf.network.okhttp.Okhttp3Utils;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.logging.Logger;

import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2017/8/15.
 */

public class RetrofitUtils {

    /**
     *
     *  验证Retrofit2 + RxJava2 + Okhttp3 框架使用
     *
     *
     */

    private static final String TAG = "RetrofitUtils";

    private static final Logger logger = Logger.getLogger("RetrofitUtils");

    private static RetrofitUtils instance = null;

    private RetrofitUtils(){}

    public static RetrofitUtils getInstance(){
        if (instance == null){
            synchronized (RetrofitUtils.class){
                if (instance == null){
                    instance = new RetrofitUtils();
                }
            }
        }
        return instance;
    }

    public Retrofit createRetrofit(String url, Converter.Factory factory){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 添加RxJava2适配器
                .addConverterFactory(factory)  //JSon对象处理
//                .addConverterFactory(ScalarsConverterFactory.create()) //基本数据类型转化，字符串，布尔，整形，长整形等等
                .client(Okhttp3Utils.getInstance().createOkhttp()) //自定义Okhttp，实际源码中会判空，如果为空就会创建对应的 okhttp实体
                .build();
        return retrofit;
    }

    public <T> T createService(String url, Converter.Factory factory, Class<T> classz){
        Retrofit retrofit = createRetrofit(url,factory);
        T instance = retrofit.create(classz);
        return instance;
    }



}
