package com.qy.order;

import com.qy.config.MyRoleConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
//@EnableDiscoveryClient
@EnableFeignClients
//@RibbonClients(value = {@RibbonClient(name = "book-service", configuration = MyRoleConfig.class)})
public class OrderApplication {
    public static void main(String[] args) {
//        ConfigurableApplicationContext applicationContext = SpringApplication.run(OrderApplication.class);

        StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
        StackTraceElement[] var2 = stackTrace;
        int var3 = stackTrace.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            StackTraceElement stackTraceElement = var2[var4];
            if ("main".equals(stackTraceElement.getMethodName())) {
                System.out.println(stackTraceElement.getClassName());
            }
        }
    }

//    @Bean
//    @LoadBalanced
//    public RestTemplate getRestTemplate(RestTemplateBuilder builder){
//        return builder.build();
//    }


}
