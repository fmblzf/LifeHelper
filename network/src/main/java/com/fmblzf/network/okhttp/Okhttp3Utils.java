package com.fmblzf.network.okhttp;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by fmblzf on 2017/8/11.
 */

public class Okhttp3Utils {

    private final static String TAG = "Okhttp3Utils";

    private static Logger logger = Logger.getLogger(TAG);

    private static Okhttp3Utils instance;
    //连接超时时间，单位s
    private static int CONNECT_TIMEOUT = 30;
    //写超时时间，单位s
    private static int WRITE_TIMEOUT = 30;
    //读超时时间，单位s
    private static int READ_TIMEOUT = 30;
    //文件的缓存文件夹
    private static final String CACHE_FILE = Environment.getExternalStorageDirectory()+"/okhttp_cache";
    //文件缓存的文件夹对象
    private static File cacheFile = null;
    //文件的缓存文件大小
    private static final int CACHE_FILE_SIZE = 1024 * 10 * 10;
    //JSON数据类型
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    //Form表单数据提交
    public static final MediaType FORM = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");

    private Okhttp3Utils() {
    }

    public static Okhttp3Utils getInstance(){
        if (instance == null){
            synchronized (Okhttp3Utils.class){
                if (instance == null){
                    instance = new Okhttp3Utils();
                }
            }
        }
        return instance;
    }


    public OkHttpClient createOkhttp(){
        //创建缓存文件夹
        if (cacheFile == null){
            cacheFile = new File(CACHE_FILE);
            if (!cacheFile.exists()){
                cacheFile.mkdirs();
            }
        }
        Cache cache = new Cache(cacheFile,CACHE_FILE_SIZE);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT,TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT,TimeUnit.SECONDS)
                .retryOnConnectionFailure(true) //连接失败的时候重新请求连接
                .addInterceptor(new LoggingInterceptor("Application"))  //Application拦截器，就是在Response的时候回拦截一次
                .addNetworkInterceptor(new LoggingInterceptor("Network")) //这个是网络拦截器，会在Request和Response的时候都进行一次拦截
                .cache(cache) //开启缓存
                .build();
        return client;
    }

    /**
     * 日志拦截器
     */
    public static class LoggingInterceptor implements Interceptor {
        /**
         * 拦截器类型 应用拦截器，网络拦截器
         */
        private String  interceptorType = "";

        public LoggingInterceptor(String type) {
            this.interceptorType = type;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            logger.info(String.format("%s:Sending request %s on %s%n%s",interceptorType,
                    request.url(), chain.connection(), request.headers()));
            Log.e(TAG,request.url().toString());

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            logger.info(String.format("%s:Received response for %s in %.1fms%n%s",interceptorType,
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));

            return response;
        }
    }

    public String execute(String url, RequestBody body,CacheControl cacheControl)throws Exception {
        Request.Builder builder = new Request.Builder();
        if (url == null || url == ""){
            throw new Exception("url is null");
        }
        builder.url(url);
        if (body != null){
            builder.post(body);
        }
        if (cacheControl != null){
            builder.cacheControl(cacheControl);
        }
        Request request = builder.build();
        OkHttpClient client = createOkhttp();
        Call call = client.newCall(request);
        Response response = call.execute();
        return response.body().string();
    }
    public String execute(String url)throws Exception {
        return execute(url,null,null);
    }
    public String execute(String url,CacheControl cacheControl)throws Exception {
        return execute(url,null,cacheControl);
    }
    public String execute(String url,RequestBody body)throws Exception {
        return execute(url,body,null);
    }



    public void enqueue(String url, RequestBody body, CacheControl cacheControl,final ResultCallback callback)throws Exception {
        Request.Builder builder = new Request.Builder();
        if (url == null || url == ""){
            throw new Exception("url is null");
        }
        builder.url(url);
        if (body != null){
            builder.post(body);
        }
        if (cacheControl != null){
            builder.cacheControl(cacheControl);
        }
        Request request = builder.build();
        OkHttpClient client = createOkhttp();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFail(call,e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callback.onResponse(call,response.body().string());
            }
        });
    }
    public void enqueue(String url, ResultCallback callback)throws Exception {
        enqueue(url,null,null,callback);
    }
    public void enqueue(String url,CacheControl cacheControl, ResultCallback callback)throws Exception {
        enqueue(url,null,cacheControl,callback);
    }
    public void enqueue(String url,RequestBody body, ResultCallback callback)throws Exception {
        enqueue(url,body,null,callback);
    }

    public static interface ResultCallback{
        /**
         * 请求失败的回调函数
         * @param call
         * @param e
         */
        public void onFail(Call call, IOException e);

        /**
         * 请求成功的回调函数
         * @param call
         * @param response
         * @throws IOException
         */
        void onResponse(Call call, String response) throws IOException;
    }

}
