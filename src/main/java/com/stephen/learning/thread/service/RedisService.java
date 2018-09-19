package com.stephen.learning.thread.service;

import com.stephen.learning.thread.util.JedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

/**
 * @Auther: jack
 * @Date: 2018/9/19 07:49
 * @Description: redis操作
 */
@Slf4j
@Service
public class RedisService {
    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private JedisCluster jedisCluster;

    public long lpush(String key, String value) {
        JedisCommands commands = null;
        try {
            commands = JedisUtils.getJedisCommands(jedisCluster, jedisPool);
            return commands.lpush(key, value);
        } catch (JedisException e) {
            log.error("action=lpush, key:{}, value:{} to call back, occur exception", key, value, e);
        } finally {
            JedisUtils.releaseJedisConnection(commands);
        }
        return Long.valueOf(0);
    }

    public String rpop(String key){
        JedisCommands commands = null;
        try {
            commands = JedisUtils.getJedisCommands(jedisCluster, jedisPool);
            return commands.rpop(key);
        } catch (JedisException e) {
            log.error("action=lpop, key:{} to call back, occur exception", key, e);
        } finally {
            JedisUtils.releaseJedisConnection(commands);
        }
        return null;
    }

}
