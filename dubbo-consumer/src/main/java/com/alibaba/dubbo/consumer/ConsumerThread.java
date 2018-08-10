package com.alibaba.dubbo.consumer;

import com.alibaba.dubbo.demo.DemoService;
import com.thinkbit.engine.api.service.CityService;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConsumerThread {


    public static ExecutorService executorService = null;

    public static volatile boolean isRun = false;

    public static volatile int count = 0;


    static {
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
    }

    public static void main(String[] args) {
        //测试常规服务
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("consumer.xml");
        context.start();
        System.out.println("consumer 多线程调用压力测试 start");

        DemoService demoService = context.getBean(DemoService.class);
        System.out.println(demoService.getPermissions(1L));

        CityService cityService = context.getBean(CityService.class);
        System.out.println(cityService.hello("thinkbit"));


        for (int i = 0; i < Runtime.getRuntime().availableProcessors() * 2; i++) {

            executorService.execute(() -> {
                while (true) {
                    if (isRun) {
                        cityService.hello("thinkbit");
                        demoService.getPermissions(1L);
                        count++;
                    }
                }
            });
        }

        long current = System.currentTimeMillis();
        System.out.println("服务运行开始时间: " + current);
        long stop = current + 1000 * 10;
        isRun = true;


        while (stop > System.currentTimeMillis()) {

        }
        isRun = false;

        System.out.println("服务运行结束时间: " + System.currentTimeMillis());
        System.out.println("10秒递增数量：" + count);
        System.exit(0);
    }
}
