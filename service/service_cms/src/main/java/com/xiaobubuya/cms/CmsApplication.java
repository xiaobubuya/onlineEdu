package com.xiaobubuya.cms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: xiaobubuya
 * @Description:
 * @Date Created in 2021-06-21 18:36
 * @Modified By:
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"com.xiaobubuya"})
@MapperScan("com.xiaobubuya.cms.mapper")
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }
}
