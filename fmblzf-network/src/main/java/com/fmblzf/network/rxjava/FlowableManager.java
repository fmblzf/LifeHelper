package com.fmblzf.network.rxjava;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/16.
 */

public class FlowableManager {

    private static final String TAG = "FlowableManager";

    public static <T,R> Flowable<R> convertType(Flowable<T> flowable,final IConvertDataType<T,R> convertDataType){
        Flowable<T> tFlowable = flowable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        Flowable<R> rFlowable = tFlowable.flatMap(new Function<T, Flowable<R>>() {
            @Override
            public Flowable<R> apply(T t) throws Exception {
                return Flowable.just(convertDataType.convert(t));
            }
        });
        return rFlowable;
    }

    public static class ConvertDataType<T,R> implements IConvertDataType{

        @Override
        public Object convert(Object o) {
            return null;
        }
    }

    public static interface IConvertDataType<T,R>{
        public R convert(T t);
    }

}
