package com.tcdt.qlnvkhoachphi;

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
@EntityScan(basePackages = { "com.tcdt.qlnvkhoachphi.entities", "com.tcdt.qlnvkhoachphi.table" })
//@EnableFeignClients
public class QlnvKHoachPhiApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(QlnvKHoachPhiApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(QlnvKHoachPhiApplication.class, args);
	}
}
