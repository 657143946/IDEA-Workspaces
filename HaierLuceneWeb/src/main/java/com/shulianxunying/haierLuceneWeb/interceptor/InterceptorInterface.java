package com.shulianxunying.haierLuceneWeb.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Created by ChrisLee on 15-4-30.
 * 拦截器接口
 */
public interface InterceptorInterface {
    public boolean intercept(HttpServletRequest request, HttpServletResponse response, Method method);
}
