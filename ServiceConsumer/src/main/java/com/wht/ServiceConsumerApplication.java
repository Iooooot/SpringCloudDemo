package com.wht;

import com.wht.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ServiceConsumerApplication {

    @Autowired
    FeignService feignService;

    public static void main(String[] args) {
        SpringApplication.run(ServiceConsumerApplication.class, args);
    }

    @RestController
    public class EchoController {

        @GetMapping(value = "/test/{string}")
        public String echo(@PathVariable String string) {
            return feignService.echo(string);
        }
    }
}
