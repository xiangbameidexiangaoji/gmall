package com.sxt.mall.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

@RestController
public class ThreadPoolController {
    @Qualifier("mainThreadPoolExecutor")
    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @RequestMapping("/thread/status")
    public Map threadPoolStatue(){
        Map<String,Object> map = new HashMap<>();
        int corePoolSize = this.threadPoolExecutor.getCorePoolSize();
        int poolSize = this.threadPoolExecutor.getPoolSize();
        int activeCount = this.threadPoolExecutor.getActiveCount();
        this.threadPoolExecutor.shutdown();
        map.put("核心线程数", corePoolSize);
        map.put("池中当前线程数", poolSize);
        map.put("正在执行任务的线程数量", activeCount);
        return map;
    }
}
