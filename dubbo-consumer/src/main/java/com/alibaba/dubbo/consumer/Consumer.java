package com.alibaba.dubbo.consumer;

import com.alibaba.dubbo.demo.DemoService;
import com.thinkbit.engine.api.service.CityService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Consumer {
    public static void main(String[] args) {
        //测试常规服务
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("consumer.xml");
        context.start();
        System.out.println("consumer start");

        DemoService demoService = context.getBean(DemoService.class);
        System.out.println("consumer demo");
        System.out.println(demoService.getPermissions(1L));

        CityService cityService = context.getBean(CityService.class);
        System.out.println("consumer city");
        System.out.println(cityService.hello("thinkbit"));
        while (true){

        }
    }
}
