package com.lsz.info.push.config;

import cn.hutool.core.io.resource.ResourceUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.util.Date;

@Configuration
@Data
public class EsConfig {

    public static final String serverUri = "http://lsz.env.com:9200";
    public static String DSL_SEARCH;
    @Value("${lsz.es.index:metric}")
    public String indexName;
    @Value("${lsz.es.type:my_doc}")
    public String typeName;

    @PostConstruct
    public void init() {
        DSL_SEARCH = ResourceUtil.readStr("dsl/search.json", Charset.forName("utf-8"));
    }

    @Bean
    public JestClient jestClient() {
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
