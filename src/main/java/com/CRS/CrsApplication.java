package com.CRS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class CrsApplication  extends SpringBootServletInitializer{

	public static void main(String[] args) {
		System.setProperty("server.servlet.context-path", "/CRS");
		SpringApplication.run(CrsApplication.class, args);
	}
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CrsApplication.class);
    }

}
