package com.stephen.learning.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.stephen.learning.thread.dispatcher.WorkerDispatcher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

/**
 * @Auther: jack
 * @Date: 2018/9/18 23:55
 * @Description:
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {

        ConfigurableApplicationContext applicationContext=SpringApplication.run(Application.class, args);
        //使用guava创建
        ThreadFactoryBuilder threadFactory = new ThreadFactoryBuilder();
        threadFactory.setNameFormat("worker-dispatcher-%d");
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor(threadFactory.build());
        //设置为守护线程
        asyncTaskExecutor.setDaemon(true);

        WorkerDispatcher workerDispatcher=(WorkerDispatcher)applicationContext.getBean("ticketWorkerDispatcher");
        asyncTaskExecutor.submit(workerDispatcher);
    }
}
