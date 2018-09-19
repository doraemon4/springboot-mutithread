package com.stephen.learning.thread.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import redis.clients.jedis.*;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Auther: jack
 * @Date: 2018/9/19 00:57
 * @Description:
 */
@Configuration
public class RedisConfig {
    @NotNull
    @Value("${redis.maxIdle:15}")
    private int maxIdle;

    @NotNull
    @Value("${redis.maxTotal:15}")
    private int maxTotal;

    @NotNull
    @Value("${redis.maxWaitMillis:10}")
    private long maxWaitMillis;

    @NotNull
    @Value("${redis.minIdle:5}")
    private int minIdle;

    @NotNull
    @Value("${redis.dbIndex:5}")
    private int dbIndex;

    @NotNull
    @Value("#{'${redis.addressList}'.split(',')}")
    private List<String> addressList;

    @Value("${redis.password}")
    private String password;

    @Bean(destroyMethod = "close")
    @Primary
    public JedisPool getJedis() {

        if (addressList.size() > 1) {
            return null;
        }
        String hostport = addressList.get(0);
        String[] hp = hostport.split(":");

        JedisPoolConfig config = new JedisPoolConfig();

        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setMaxWaitMillis(maxWaitMillis);

        if (StringUtils.isBlank(password)) {
            return new JedisPool(config, hp[0], Integer.parseInt(hp[1]), Protocol.DEFAULT_TIMEOUT, null, dbIndex);
        } else {
            return new JedisPool(config, hp[0], Integer.parseInt(hp[1]), Protocol.DEFAULT_TIMEOUT, password, dbIndex);
        }
    }

    @Bean(destroyMethod = "close")
    @Primary
    public JedisCluster getJedisCluster() {
        if (addressList.size() == 1) {
            return null;
        }
        Set<HostAndPort> jedisClusterNodes = new HashSet<>();
        //Jedis Cluster will attempt to discover cluster nodes automatically
        for (String hostAndPort : addressList) {
            String[] hp = hostAndPort.split(":");

            jedisClusterNodes.add(new HostAndPort(hp[0], Integer.parseInt(hp[1])));
        }
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxIdle(maxIdle);
        config.setMaxTotal(maxTotal);
        config.setMaxWaitMillis(maxWaitMillis);
        config.setMinIdle(minIdle);
        config.setTestWhileIdle(true);
        JedisCluster jedisCluster = new JedisCluster(jedisClusterNodes, Protocol.DEFAULT_TIMEOUT, config);
        return jedisCluster;
    }

}
