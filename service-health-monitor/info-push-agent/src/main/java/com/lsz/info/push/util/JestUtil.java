package com.lsz.info.push.util;

import cn.hutool.core.date.DateUtil;
import com.google.gson.*;
import com.lsz.info.push.config.DateAdapter;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

import java.lang.reflect.Type;
import java.util.Date;

public class JestUtil {

    public static final String serverUri = "http://lsz.env.com:9200";

    public static JestClient getJestClient() {
        Gson gson = new GsonBuilder().setLenient().registerTypeAdapter(Date.class, new DateAdapter()).create();

        JestClientFactory factory = new JestClientFactory();
        HttpClientConfig.Builder httpClientConfig = new HttpClientConfig
                .Builder(serverUri)
                .multiThreaded(true)
                .gson(gson)
                .connTimeout(30000)
                .readTimeout(30000);
        factory.setHttpClientConfig(httpClientConfig.build());
        return factory.getObject();
    }

}
