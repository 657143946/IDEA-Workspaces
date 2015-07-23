package com.shulianxunying.haierLuceneWeb.controller.haier;

import com.mongodb.*;
import com.mongodb.util.JSON;
import com.shulianxunying.haierLuceneWeb.utils.JsonConvert;
import com.shulianxunying.haierLuceneWeb.utils.Reader;

import javax.json.Json;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Created by ChrisLee.
 */
public class FindMongo {
    private static Properties config;
    private static MongoClient client;

    static {
        try {
            config = Reader.readProperties("/properties/mongo.properties", Reader.UTF8);
            client = new MongoClient(config.getProperty("host"), Integer.valueOf(config.getProperty("port")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Result> findCombineInfoByIds(String... ids) {
        /**
         * 构建查询
         */
        List<DBObject> queryIds = new LinkedList<>();
        for (String id : ids) {
            queryIds.add(new BasicDBObject("cv_id", id));
        }
        DBObject query = new BasicDBObject("$or", queryIds);
        /**
         * 搜索字段
         */
        BasicDBObject fields = new BasicDBObject();
        fields.put("_id", 0);
        fields.put("cv_id", 1);
        fields.put("cv_origin", 1);
        fields.put("user_img", 1);
        fields.put("last_refresh", 1);
        fields.put("living", 1);
        fields.put("work_now", 1);
        fields.put("self_eval", 1);
        fields.put("exp_location", 1);
        fields.put("exp_m_salary", 1);
        fields.put("work_time", 1);
        fields.put("academic_name", 1);
        fields.put("gender", 1);
        fields.put("name", 1);
        fields.put("category", 1);

        DB combine = client.getDB("combine");
        if (config.getProperty("user") != null && !"".equals(config.getProperty("user"))) {
            combine.authenticate(config.getProperty("user"), config.getProperty("password").toCharArray());
        }
        List<Result> ret = new LinkedList<>();
        DBCursor cursor = combine.getCollection("resume_info").find(query, fields).limit(ids.length);
        while (cursor.hasNext()) {
            DBObject resume = cursor.next();
            Result result = new Result(resume);
            ret.add(result);
        }
        return ret;
    }

    public static void main(String[] args) {
        String[] ids = {
                "5e64ae8487c2ee537c0ba2a2j",
                "28efae84a1ccac53a4c4be49j",
                "20-25282511"


        };
        long start = System.currentTimeMillis();
        findCombineInfoByIds(ids);
        System.out.println("耗时：" + (System.currentTimeMillis() - start) / 1000);
        String a = (String)null;
    }
}
