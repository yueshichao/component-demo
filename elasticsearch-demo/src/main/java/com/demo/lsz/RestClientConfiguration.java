package com.demo.lsz;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RestClientConfiguration extends AbstractFactoryBean<RestHighLevelClient> {

    @Value("${elasticsearch.host}")
    private String host;

    private RestHighLevelClient restHighLevelClient;

    @Override
    protected RestHighLevelClient createInstance() throws Exception {
        try {
            // 如果有多个节点，构建多个HttpHost
            restHighLevelClient = new RestHighLevelClient(
                    RestClient.builder(
                            new HttpHost(host, 9200, "http")));
        } catch (Exception e) {
            log.error("{}", e);
        }
        return restHighLevelClient;
    }

    @Override
    public Class<?> getObjectType() {
        return RestHighLevelClient.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public void destroy() throws Exception {
        if (restHighLevelClient != null) {
            restHighLevelClient.close();
        }
    }



}
