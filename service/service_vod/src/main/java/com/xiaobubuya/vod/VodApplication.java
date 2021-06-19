package com.xiaobubuya.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages={"com.xiaobubuya"})
public class VodApplication {

	public static void main(String[] args) {
		SpringApplication.run(VodApplication.class, args);
	}
}