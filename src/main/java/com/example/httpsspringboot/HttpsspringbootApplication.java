package com.example.httpsspringboot;

import com.example.httpsspringboot.gateway.RequestGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HttpsspringbootApplication implements CommandLineRunner {

    final
    RequestGateway gateway;

    public HttpsspringbootApplication(RequestGateway gateway) {
        this.gateway = gateway;
    }

    public static void main(String[] args) {
        SpringApplication.run(HttpsspringbootApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        gateway.mustPerformHttpsCall();
    }
}
