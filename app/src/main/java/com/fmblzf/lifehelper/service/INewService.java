package com.fmblzf.lifehelper.service;

import com.google.gson.JsonObject;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/8/16.
 */

public interface INewService {

    /**
     *  GET获取数据
     * @param type
     * @param key
     * @return
     */
    @GET("toutiao/index")
    Flowable<JsonObject> getNewTop(@Query("type") String type, @Query("key") String key);

    /**
     *  POST 表单提交，获取数据
     * @param type
     * @param key
     * @return
     */
    @FormUrlEncoded  //表单url标记
    @POST("toutiao/index")
    Flowable<JsonObject> getNew(@Field("type")String type, @Field("key")String key);

}
