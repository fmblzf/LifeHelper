package com.fmblzf.annotion.view;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作者： fmblzf
 * 时间：2017/9/1.
 */
@Documented
@Target(value={ElementType.TYPE})
@Inherited
@Retention(value= RetentionPolicy.RUNTIME)
public @interface FmblzfLayout {
    public int layoutResId();
}
