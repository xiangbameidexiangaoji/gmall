package com.sxt.mall.locks.service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestThread {
    //    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        FutureTask<String> task = new FutureTask<>(new Thread01());
//        new Thread(task).start();
//        String s = task.get();
//        System.out.println(s);
//    }
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(()->{
               System.out.println("当前线程开始:" + Thread.currentThread());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("当前线程结束:" + Thread.currentThread());
            });
            service.submit(thread);
        }
    }
}

/**
 * 泛型返回值
 */
class Thread01 implements Callable<String> {

    @Override
    public String call() throws Exception {
        return "ok";
    }
}