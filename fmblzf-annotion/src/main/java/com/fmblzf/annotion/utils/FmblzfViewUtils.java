package com.fmblzf.annotion.utils;

import android.app.Activity;
import android.view.View;

import com.fmblzf.annotion.view.FmblzfEvent;
import com.fmblzf.annotion.view.FmblzfEventType;
import com.fmblzf.annotion.view.FmblzfLayout;
import com.fmblzf.annotion.view.FmblzfView;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 作者： fmblzf
 * 时间：2017/9/1
 */

public class FmblzfViewUtils {
    /**
     * 注册Activity实现view的初始化操作
     * @param context
     * @throws Exception
     */
    public static void register(Activity context) throws Exception {
        Class<?> clasz = context.getClass();
        boolean isTypeAnn = clasz.isAnnotationPresent(FmblzfLayout.class);
        if (!isTypeAnn)
            return ;

        FmblzfLayout layout = clasz.getAnnotation(FmblzfLayout.class);
        if (layout == null)
            return ;

        int layoutId = layout.layoutResId();
        context.setContentView(layoutId);

        //初始化属性
        initAttrs(context,clasz);
        //初始化监听事件
        initEvents(context,clasz);

    }

    /**
     * 初始化Activity中的属性值
     * @param context
     * @param clasz
     * @throws Exception
     */
    private static void initAttrs(Activity context, Class<?> clasz) throws Exception {
        Field[] fields = clasz.getFields();
        if (fields == null || fields.length == 0)
            return;
        for (Field field:fields){
            boolean isFieldAnn = field.isAnnotationPresent(FmblzfView.class);
            if (!isFieldAnn)
                continue;
            FmblzfView fmblzfView = field.getAnnotation(FmblzfView.class);
            if (fmblzfView == null)
                continue;
            int viewId = fmblzfView.viewId();
            View view = context.findViewById(viewId);
            if (view == null){
                throw new Exception("viewId="+viewId+" not find !");
            }
            try {
                field.set(context,view);
            }catch (Exception e){
                throw new Exception("view of "+viewId+" cast to "+field.getType()+" fail!");
            }
        }
    }

    /**
     * 初始化Activity中的事件绑定
     * @param context
     * @param clasz
     */
    private static void initEvents(Activity context, Class<?> clasz) {
        Method[] methods = clasz.getMethods();
        if (methods == null || methods.length == 0)
            return;
        for (Method method:methods) {
            boolean isMethodAnn = method.isAnnotationPresent(FmblzfEvent.class);
            if (!isMethodAnn)
                continue;
            FmblzfEvent event = method.getAnnotation(FmblzfEvent.class);
            if (event == null)
                continue;
            dispatchEvent(context,method,event);
        }
    }

    private static void dispatchEvent(Activity context, Method method,FmblzfEvent event) {
        int viewId = event.viewId();
        FmblzfEventType eventType = event.eventType();
        View view = context.findViewById(viewId);
        if (eventType == FmblzfEventType.CLICK){
            //点击事件
            bindClick(context,view,method);
        }else if (eventType == FmblzfEventType.LONG_CLICK){
            //长按事件
            bindLongClick(context,view,method);
        }else if (eventType == FmblzfEventType.DOUBLE_CLICK){
            //双击事件
            bindDoubleClick(context,view,method);
        }
    }

    /**
     * 绑定单击事件
     * @param context
     * @param view
     * @param method
     */
    private static void bindClick(final Activity context, View view, final Method method) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    method.invoke(context,v);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 绑定长按事件
     * @param context
     * @param view
     * @param method
     */
    private static void bindLongClick(final Activity context, View view, final Method method) {
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                try {
                    method.invoke(context,v);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
    }

    private static void bindDoubleClick(Activity context, View view, Method method) {

    }


}
