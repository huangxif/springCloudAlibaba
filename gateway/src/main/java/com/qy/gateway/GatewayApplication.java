package com.qy.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author 17691127401
 */
@SpringBootApplication
public class GatewayApplication {
    public static void main(String[] args) {
//        SpringApplication.run(GatewayApplication.class, args);
        //char, byte, short, int, Character, Byte, Short, Integer, String, or an enum
        int a = 1;

//        ForkJoinTask
    }

    public static class MyFrokJoin extends RecursiveTask<Long> {


        private static final long serialVersionUID = 9193939169735098802L;

        @Override
        protected Long compute() {
            return null;
        }
    }
}
