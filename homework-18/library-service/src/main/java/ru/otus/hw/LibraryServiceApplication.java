package ru.otus.hw;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.sql.SQLException;

@SpringBootApplication
@EnableDiscoveryClient
public class LibraryServiceApplication {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(LibraryServiceApplication.class, args);

        System.out.printf("Чтобы перейти на страницу сайта открывай: %n%s%n",
                "http://localhost:8080/books");

           Console.main(args);
    }
}
