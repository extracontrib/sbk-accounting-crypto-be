package com.breadcrumb.kit.accounting.crypto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.ZoneOffset;
import java.util.TimeZone;

@SpringBootApplication(scanBasePackages = "com.breadcrumb.kit.accounting.crypto")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneOffset.UTC));
    }
}
