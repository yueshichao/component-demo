package com.lsz.shardingjdbc.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
@Configuration
public class ShardingConfig {

    // 逻辑库名 m1与连接池 的映射
    private Map<String, DataSource> createDataSourceMap() {
        // 创建dataSource
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://192.168.0.106:3306/order_db?useUnicode=true&useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");

        // 映射逻辑库名m1
        Map<String, DataSource> map = new HashMap<>();
        map.put("m1", dataSource);
        return map;
    }

    // 主键生成策略
    private KeyGeneratorConfiguration getKeyGeneratorConfiguration() {
        KeyGeneratorConfiguration keyGeneratorConfiguration = new KeyGeneratorConfiguration("SNOWFLAKE", "order_id");
        return keyGeneratorConfiguration;
    }

    // yml配置项：spring.shardingsphere.sharding.tables
    private TableRuleConfiguration getTableRuleConfiguration() {
        // 1. 数据表分片情况
        TableRuleConfiguration tableRuleConfiguration = new TableRuleConfiguration("t_order", "m1.t_order_${1..2}");
        // 2. 主键生成策略
        tableRuleConfiguration.setKeyGeneratorConfig(getKeyGeneratorConfiguration());
        // 3. 分片策略：分片键和分片算法
        InlineShardingStrategyConfiguration inlineShardingStrategy = new InlineShardingStrategyConfiguration("order_id", "t_order_$->{order_id % 2 + 1}");
        tableRuleConfiguration.setTableShardingStrategyConfig(inlineShardingStrategy);
        return tableRuleConfiguration;
    }


    @Bean
    public DataSource getShardingDataSource() throws SQLException {
        log.info("使用java config方式配置sharding-jdbc");
        ShardingRuleConfiguration shardingRule = new ShardingRuleConfiguration();
        shardingRule.getTableRuleConfigs().add(getTableRuleConfiguration());
        Properties properties = new Properties();
        properties.put("sql.show", "true");
        return ShardingDataSourceFactory.createDataSource(createDataSourceMap(), shardingRule, properties);
    }



}
