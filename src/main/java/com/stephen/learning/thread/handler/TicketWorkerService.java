package com.stephen.learning.thread.handler;

import com.stephen.learning.thread.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: jack
 * @Date: 2018/9/19 08:16
 * @Description:
 */
@Slf4j
@Service
public class TicketWorkerService implements WorkerService {
    @Autowired
    private RedisService redisService;

    @Override
    public Object buyTicket(String ticketType) {
        return redisService.rpop(ticketType);
    }
}
