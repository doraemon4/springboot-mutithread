package com.stephen.learning.thread.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Auther: jack
 * @Date: 2018/9/19 08:11
 * @Description: 买到票后的操作
 */
@Slf4j
@Component
public class TicketWorkerHandler implements WorkerHandler {
    @Override
    public void execute(Object object) {
        log.info("买到票:{}",object);
    }
}
