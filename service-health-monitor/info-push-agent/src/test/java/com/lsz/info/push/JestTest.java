package com.lsz.info.push;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import com.lsz.info.push.vo.ServiceMetric;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.indices.CreateIndex;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;

import static com.lsz.info.push.config.EsConfig.DSL_SEARCH;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PushMain.class})
public class JestTest {

    @Resource
    JestClient jestClient;
    @Resource
    RedissonClient redissonClient;


    String indexName = "metric";
    String typeName = "my_doc";
    String dls_search = "";

    @Before
    public void init() {
        dls_search = DSL_SEARCH;
    }

    @Test
    public void test() {
        log.info("this is a test case...");
        RBucket<Object> bucket = redissonClient.getBucket("lsz_test", new StringCodec());
        bucket.set(1);
        ThreadUtil.sleep(1000);
    }


    @Test
    public void testSearch() throws IOException {

        String query = StrUtil.format(dls_search, "info-push-service", DateUtil.beginOfMonth(new Date()).getTime(), DateUtil.endOfMonth(new Date()).getTime());
        log.info("query = {}", query);
        Search search = new Search.Builder(query)
                .addIndex(indexName)
                .addType(typeName)
                .build();
        SearchResult result = jestClient.execute(search);
        log.info("search = {}, result = {}", search, result);
    }

    @Test
    public void testCreateIndex() throws IOException {
        CreateIndex createIndex = new CreateIndex.Builder(indexName).build();
        JestResult result = jestClient.execute(createIndex);
        log.info("create result = {}", result);
    }

    @Test
    public void testInsert() throws IOException {
        Index index = new Index.Builder(ServiceMetric.defaultMetric())
                .index(indexName)
                .type(typeName)
                .id("2")
                .build();
        DocumentResult result = jestClient.execute(index);
        log.info("insert result = {}", result);
    }

}
