package com.shulianxunying.haierLuceneWeb.interceptor;

import com.shulianxunying.haierLuceneWeb.annotation.AnnotationFinder;
import com.shulianxunying.haierLuceneWeb.annotation.Pre;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by ChrisLee.
 * 拦截器
 */
public class InterceptorHandler implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * step1. 获取Method;
         * step2. 获取指定注解;
         * step3. 获取拦截器;
         * strp4. 实例化拦截器，调用拦截;
         *      if false: return false;
         * return true
         */
        Field field = handler.getClass().getDeclaredField("method");
        field.setAccessible(true);
        Method method = (Method) field.get(handler);

        Object[] annotations = AnnotationFinder.findClassAndMethodAnnotation(method, Pre.class);
        List<InterceptorInterface> interceptors = findInterceptors((Pre) annotations[0], (Pre) annotations[1]);
        for (InterceptorInterface interceptor : interceptors) {
            if (!interceptor.intercept(request, response, method)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }


    /**
     * 通过注解找到需要执行拦截器
     */
    public static List<InterceptorInterface> findInterceptors(Pre... annotations) {
        List<Class<? extends InterceptorInterface>> result = new LinkedList<>();
        List<InterceptorInterface> ret = new LinkedList<>();
        if (annotations != null) {
            for (Pre annotation : annotations) {
                if (annotation != null) {
                    result.addAll(Arrays.asList(annotation.on()));
                    result.removeAll(Arrays.asList(annotation.off()));
                }
            }
        }
        for (Class<? extends InterceptorInterface> clazz : result) {
            try {
                ret.add(clazz.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

}
