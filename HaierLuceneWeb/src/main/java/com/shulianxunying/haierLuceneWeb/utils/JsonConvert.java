package com.shulianxunying.haierLuceneWeb.utils;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by ChrisLee on 15-5-24.
 * JSON字符串帮助类
 */
public class JsonConvert {

    /**
     * json 字符串 -> 实体
     *
     * @param json  String   json字符串
     * @param clazz Class   实体的Class
     * @param <T>   泛型
     * @return T    实体
     */
    public static <T> T jsonToEntity(String json, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        T t = null;
        try {
            t = (T) objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 实体 -> json 字符串
     *
     * @param entity Object  实体
     * @return String  json字符串
     */
    public static String entityToJson(Object entity) {
        StringWriter str = new StringWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(str, entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str.toString();
    }


}
