package com.stephen.learning.thread.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import java.net.SocketTimeoutException;

/**
 * @Auther: jack
 * @Date: 2018/9/19 01:05
 * @Description:
 */
public class JedisUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(JedisUtils.class);

    public JedisUtils() {
    }

    public static JedisCommands getJedisCommands(JedisCluster jedisCluster, JedisPool jedisPool) {
        Object commands;
        if (jedisCluster == null) {
            commands = jedisPool.getResource();
        } else {
            commands = jedisCluster;
        }

        return (JedisCommands)commands;
    }

    public static void releaseJedisConnection(JedisCommands commands) {
        if (commands != null && commands instanceof Jedis) {
            Jedis jedis = (Jedis)commands;
            jedis.close();
        }

    }

    public static void logException(String message, JedisException e) {
        if (e.getCause() != null && e.getCause() instanceof SocketTimeoutException) {
            LOGGER.error(message, "操作redis超时");
        } else {
            LOGGER.error(message, e);
        }

    }

}
