package com.stephen.learning.thread.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: jack
 * @Date: 2018/9/19 08:26
 * @Description:
 */
@Slf4j
@Service
public class TicketService {
    @Autowired
    private RedisService redisService;

    public void createTicket(String type,int num){
        for(int i=0;i<num;i++){
            redisService.lpush(type,type+i);
        }
    }
}
