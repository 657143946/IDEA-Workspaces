package com.shulianxunying.haierLuceneWeb.annotation;



import com.shulianxunying.haierLuceneWeb.interceptor.InterceptorInterface;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by ChrisLee on 15-4-30.
 *
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Pre {
    Class<? extends InterceptorInterface>[] on() default {};

    Class<? extends InterceptorInterface>[] off() default {};
}
