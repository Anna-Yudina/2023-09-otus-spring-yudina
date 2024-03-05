package ru.otus.hw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "ru.otus.hw.feign")
public class VisualizerLibraryClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(VisualizerLibraryClientApplication.class, args);

        System.out.printf("Чтобы перейти на страницу сайта открывай: %n%s%n",
                "http://localhost:8088/authors");
    }
}
