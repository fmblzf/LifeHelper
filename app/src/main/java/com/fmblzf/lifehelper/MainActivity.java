package com.fmblzf.lifehelper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.fmblzf.lifehelper.model.NewInfo;
import com.fmblzf.lifehelper.service.INewService;
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

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.glide_layout);



    }

    public void init(){
        String url = "http://v.juhe.cn/";
        INewService service = RetrofitUtils.getInstance().createService(url, GsonConverterFactory.create() ,INewService.class);
        Flowable<JsonObject> jsonFlowable = service.getNew("top","dd7bba438f17877f6790d49087424418");
        FlowableManager.convertType(jsonFlowable,new JsonConvertClass()).subscribe(new Subscriber<List<NewInfo>>() {
            @Override
            public void onSubscribe(Subscription s) {
                Log.d(TAG,"onSubscribe");
            }

            @Override
            public void onNext(List<NewInfo> newInfos) {
                Log.d(TAG,"onNext=="+newInfos.toString());
            }

            @Override
            public void onError(Throwable t) {
                Log.d(TAG,"onError");
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"onComplete");
            }
        });
    }


    public static class JsonConvertClass implements FlowableManager.IConvertDataType<JsonObject,List<NewInfo>>{

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
