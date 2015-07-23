package com.shulianxunying.haierLuceneWeb.interceptor;


import com.shulianxunying.haierLuceneWeb.utils.ResponseUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Created by ChrisLee on 15-5-24.
 * 抽象类，扩展了拦截器的功能
 */
public abstract class Interceptor implements InterceptorInterface {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Method method;

    @Override
    public boolean intercept(HttpServletRequest request, HttpServletResponse response, Method method) {
        this.request = request;
        this.response = response;
        this.method = method;
        return this.interceptor();
    }

    /**
     * 子类实现
     *
     * @return boolean 是否拦截
     */
    public abstract boolean interceptor();

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public Method getMethod() {
        return method;
    }

    /**
     * 向response中写json
     */
    public void jsonReturn(Object model) {
        ResponseUtils.jsonReturn(response, model);
    }

    /**
     * 向response中写json
     */
    public void jsonReturn(String json) {
        ResponseUtils.jsonReturn(response, json);
    }

    /**
     * 利用response跳转页面
     */
    public void redirectPage(String redirect) {
        ResponseUtils.redirectPage(response, redirect);
    }

}
