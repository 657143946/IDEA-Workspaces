package com.shulianxunying.haierLuceneWeb.utils;

import java.io.*;
import java.util.Properties;

/**
 * Created by AbnerLee on 15-1-20.
 */
public class Reader {
    public final static String UTF8 = "utf-8";
    public final static String GB2312 = "GB2312";

    public static InputStream getResourcesInputStream(String resource) {
        return Reader.class.getResourceAsStream(resource);
    }

    public static Properties readProperties(String property, String encoding) throws IOException {
        Properties p = new Properties();
        p.load(new InputStreamReader(getResourcesInputStream(property), encoding));
        return p;
    }

    public static BufferedReader getReader(String resource, String encoding) throws UnsupportedEncodingException {
        return new BufferedReader(new InputStreamReader(getResourcesInputStream(resource), encoding));
    }



}
