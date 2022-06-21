package com.lsz.info.push.service;

import com.lsz.info.push.config.EsConfig;
import com.lsz.info.push.vo.ServiceMetric;
import io.searchbox.client.JestClient;
import io.searchbox.core.Bulk;
import io.searchbox.core.BulkResult;
import io.searchbox.core.Index;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EsService {

    @Resource
    JestClient jestClient;
    @Resource
    EsConfig esConfig;

    public void push(List<ServiceMetric> metrics) {
        List<Index> collect = metrics.stream().map(metric ->
                new Index.Builder(metric)
                        .index(esConfig.getIndexName())
                        .type(esConfig.getTypeName())
                        .build()).collect(Collectors.toList());
        if (collect.isEmpty()) return;
        Bulk bulk = new Bulk.Builder()
                .addAction(collect)
                .build();
        log.info("push bulk = {}", bulk);
        try {
            BulkResult result = jestClient.execute(bulk);
            log.info("insert result = {}", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
