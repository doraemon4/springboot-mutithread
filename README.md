# springboot-mutithread
springboot使用多线程  
遇到的问题记录一下：
SimpleAsyncTaskExecutor开启线程的时候出现一个问题：**<font color="red">submit(Callable)线程一直不启动，传入Runnable才可以启动</font>**
