package com.lsz.info.push;

import com.lsz.info.push.util.JestUtil;
import com.lsz.info.push.vo.ServiceMetric;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.indices.CreateIndex;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;

@Slf4j
public class JestTest {


    @Test
    public void testSelect() throws IOException {

    }

    @Test
    public void testCreateIndex() throws IOException {
        JestClient jestClient = JestUtil.getJestClient();
        CreateIndex createIndex = new CreateIndex.Builder("metric").build();
        JestResult result = jestClient.execute(createIndex);
        log.info("result = {}", result);
    }

    @Test
    public void testInsert() throws IOException {
        JestClient jestClient = JestUtil.getJestClient();
        Index index = new Index.Builder(ServiceMetric.defaultMetric()).type("_doc").id("1").index("metric").build();
        DocumentResult result = jestClient.execute(index);
        log.info("result = {}", result);

    }

}
