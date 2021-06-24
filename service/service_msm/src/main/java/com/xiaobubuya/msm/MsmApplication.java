package com.xiaobubuya.msm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.xiaobubuya"})
@EnableDiscoveryClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//取消数据源自动配置
public class MsmApplication {
	public static void main(String[] args) {
		SpringApplication.run(MsmApplication.class, args);
	}
}