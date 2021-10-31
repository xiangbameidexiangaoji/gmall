package com.sxt.mall.locks.service;

public class CompletableFutureTest {
/*        public static void main(String[] args) {
            //创建线程池
            ExecutorService pool = Executors.newFixedThreadPool(10);
            System.out.println("===主线程===");
            //创建CompletableFuture
            CompletableFuture.supplyAsync(()->{
                System.out.println("当前线程开始:" + Thread.currentThread().getName());
                String uuid = UUID.randomUUID().toString();
                System.out.println("当前线程结束:" + Thread.currentThread().getName());
                int i = 10/0;
                return uuid;
            },pool).whenComplete((r,e)->{
                //whenComplete​(BiConsumer<? super T,? super Throwable> action)
                //返回与此阶段相同的结果或异常的新CompletionStage，当此阶段完成时，它将执行给定的操作
                System.out.println("方法执行完成结果:" + r);
                System.out.println("方法执行完成异常:" + e);
            });
        }*/
//    public static void main(String[] args) {
//        //创建线程池
//        ExecutorService pool = Executors.newFixedThreadPool(10);
//        System.out.println("===主线程===");
//        //创建CompletableFuture
//        CompletableFuture.supplyAsync(() -> {
//            System.out.println("当前线程开始:" + Thread.currentThread().getName());
//            String uuid = UUID.randomUUID().toString();
//            System.out.println("当前线程结束:" + Thread.currentThread().getName());
//            int i = 10/0;
//            return uuid;
//        }, pool).thenApply((r) -> {
//            System.out.println(r);
//            return r.length();
//        }).whenComplete((r,e)->{
//            if(e instanceof Throwable){
//                System.out.println(1);
//            }
//            System.out.println(r);
//        });
//    }

/*    public static void main(String[] args) {
        for(int i = 0;i<= 10;i++){
            new Thread(()->{
                tes();
            }).start();
        }
    }*/
/*    public static void tes(){
        ExecutorService pool = Executors.newFixedThreadPool(10);
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("业务代码，查询商品基本数据");
            return "小米";
        }, pool).whenComplete((r, e) -> {
            System.out.println("结果是:" + r);
            System.out.println("异常是:" + e);
        });
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("业务代码，查询商品属性数据");
            return 1;
        }, pool).whenComplete((r, e) -> {
            System.out.println("结果是:" + r);
            System.out.println("异常是:" + e);
        });
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("业务代码，查询商品营销数据");
            return "营销";
        }, pool).whenComplete((r, e) -> {
            System.out.println("结果是:" + r);
            System.out.println("异常是:" + e);
        });
        //可变参数
        CompletableFuture<Void> allOf = CompletableFuture.allOf(future1, future2, future3);
        //插队，让当前任务先执行完成
        allOf.join();
        System.out.println("所有任务执行完成");
        CompletableFuture.supplyAsync(()->{
            return "";
        },pool).whenComplete((r,e)->{

        });
    }*/
}
