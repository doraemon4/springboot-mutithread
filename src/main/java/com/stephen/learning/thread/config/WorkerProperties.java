package com.stephen.learning.thread.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Auther: jack
 * @Date: 2018/9/19 10:12
 * @Description:
 */
@Component
@ConfigurationProperties(prefix = "worker.thread")
@Data
public class WorkerProperties {
    private int coreSize;
    private int maxSize;
    private int queueSize;
    private int hungrySize;
    private long keepAliveTime=5L;
}
