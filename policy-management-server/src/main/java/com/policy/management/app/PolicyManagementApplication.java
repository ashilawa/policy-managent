package com.policy.management.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
public class PolicyManagementApplication extends SpringBootServletInitializer  {
	
	 @Override
	 protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	  return application.sources(PolicyManagementApplication.class);
	 }

	public static void main(String[] args)  {
		SpringApplication.run(PolicyManagementApplication.class, args);
	}

}
