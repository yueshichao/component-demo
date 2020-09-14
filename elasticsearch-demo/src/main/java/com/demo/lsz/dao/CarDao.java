package com.demo.lsz.dao;

import com.alibaba.fastjson.JSON;
import com.demo.lsz.entity.Car;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Repository
@Slf4j
public class CarDao {

    private final String INDEX = "test";
    private final String TYPE = "car";


    @Autowired
    RestHighLevelClient client;

    public List<Car> findAll() {
        List<Car> result = new ArrayList<>();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(/*查询全部*/);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        SearchRequest searchRequest = new SearchRequest(INDEX);
        searchRequest.types(TYPE);
        searchRequest.source(sourceBuilder);

        try {
            SearchResponse search = client.search(searchRequest);
            SearchHit[] hits = search.getHits().getHits();

            for (SearchHit hit : hits) {
                Map<String, Object> hitMap = hit.getSourceAsMap();
                log.info("es data = {}", JSON.toJSONString(hitMap));
                String name = (String) hitMap.get("name");
                Double price = (Double) hitMap.get("price");
                result.add(new Car(name, price));
            }
        } catch (IOException e) {
            log.error("{}", e);
        }
        return result;
    }


}
