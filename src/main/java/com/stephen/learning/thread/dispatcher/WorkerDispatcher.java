package com.stephen.learning.thread.dispatcher;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.stephen.learning.thread.constant.TicketType;
import com.stephen.learning.thread.handler.WorkerHandler;
import com.stephen.learning.thread.handler.WorkerHandlerWrapper;
import com.stephen.learning.thread.handler.WorkerService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.*;

/**
 * @Auther: jack
 * @Date: 2018/9/19 07:58
 * @Description:
 */
@Data
@Slf4j
public class WorkerDispatcher implements Runnable {
    private WorkerHandler workerHandler;
    private WorkerService workerService;

    private TicketType type;
    private int coreSize;
    private int maxSize;
    private int queueSize;
    private int hungrySize;
    private long keepAliveTime;
    private BlockingQueue<Runnable> queue;


    public WorkerDispatcher(TicketType type,int coreSize,int maxSize,int queueSize,int hungrySize,long keepAliveTime,WorkerHandler workerHandler,WorkerService workerService){
        this.type=type;
        this.coreSize=coreSize;
        this.maxSize=maxSize;
        this.queueSize=queueSize;
        this.hungrySize=hungrySize;
        this.keepAliveTime=keepAliveTime;
        this.workerHandler=workerHandler;
        this.workerService=workerService;
    }

    @Override
    public void run(){
        this.queue = new ArrayBlockingQueue(this.queueSize);
        StringBuffer threadNameBuffer = new StringBuffer();
        threadNameBuffer.append(String.format("worker-%s",type.getType())).append("-%d");
        ThreadFactoryBuilder threadFactoryBuilder = new ThreadFactoryBuilder();
        threadFactoryBuilder.setNameFormat(threadNameBuffer.toString());
        ExecutorService pool= new ThreadPoolExecutor(coreSize,maxSize,keepAliveTime,TimeUnit.SECONDS,queue,threadFactoryBuilder.build());

        while (true){
            try{
                if(queue.size()>this.hungrySize){
                    TimeUnit.SECONDS.sleep(5000L);
                    log.error("队列中等待任务{},超过了阀值{}",queue.size(),this.hungrySize);
                }else{
                    String ticket=(String)workerService.buyTicket(TicketType.TRAIN.getType());
                    if(StringUtils.isBlank(ticket)){
                        log.info("暂时没有票，休眠{}秒",1);
                        TimeUnit.MILLISECONDS.sleep(1000L);
                        continue;
                    }
                    pool.submit(new WorkerHandlerWrapper(ticket,workerHandler));
                }

            }catch (Exception e){
                log.error("{}买票失败",e);
            }

        }
    }
}
