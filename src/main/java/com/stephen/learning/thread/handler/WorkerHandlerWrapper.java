package com.stephen.learning.thread.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Auther: jack
 * @Date: 2018/9/19 10:34
 * @Description:
 */
@Slf4j
@AllArgsConstructor
public class WorkerHandlerWrapper implements Runnable {
    private Object object;
    private WorkerHandler workerHandler;

    @Override
    public void run() {
        workerHandler.execute(object);
    }
}
