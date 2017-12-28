package com.ysd.push;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xishengchun on 15/10/7.
 */
public class RdThreadPoolExecutor {

    public static int corePoolSize = 10;
    public static int maximumPoolSize = 128;
    public static long keepAliveTime = 1;
    public static TimeUnit unit = TimeUnit.SECONDS;
    private static final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingQueue<Runnable>(10);
    public static final ThreadFactory threadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "RdThread-" + mCount.getAndIncrement());
        }
    };


    /**
     * 推送线程池
     */
    public static ThreadPoolExecutor pushExector =
            new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, sPoolWorkQueue, threadFactory);
}
