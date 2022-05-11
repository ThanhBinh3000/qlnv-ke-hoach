package com.tcdt.qlnvkhoach;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EntityScan(basePackages = { "com.tcdt.qlnvkhoach.entities", "com.tcdt.qlnvkhoach.table" })
//@EnableFeignClients
public class QlnvKHoachApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(QlnvKHoachApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(QlnvKHoachApplication.class, args);
	}
}
