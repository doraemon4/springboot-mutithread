package com.stephen.learning.thread.config;

import com.stephen.learning.thread.constant.TicketType;
import com.stephen.learning.thread.dispatcher.WorkerDispatcher;
import com.stephen.learning.thread.handler.TicketWorkerHandler;
import com.stephen.learning.thread.handler.TicketWorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: jack
 * @Date: 2018/9/19 07:55
 * @Description:
 */
@Configuration
public class ApplicationConfig {
    @Autowired
    private WorkerProperties workerProperties;

    @Autowired
    private TicketWorkerHandler ticketWorkerHandler;

    @Autowired
    private TicketWorkerService ticketWorkerService;

    @Bean(name = "ticketWorkerDispatcher")
    public WorkerDispatcher getWorkerDispatcher(){
        WorkerDispatcher workerDispatcher=new WorkerDispatcher(
                TicketType.TRAIN,
                workerProperties.getCoreSize(),
                workerProperties.getMaxSize(),
                workerProperties.getQueueSize(),
                workerProperties.getHungrySize(),
                workerProperties.getKeepAliveTime(),
                ticketWorkerHandler,
                ticketWorkerService
        );
        return workerDispatcher;
    }
}
