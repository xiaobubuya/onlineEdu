package com.xiaobubuya.statistics.client;

import com.xiaobubuya.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    @GetMapping(value = "/ucenter/member/countregister/{day}")
    public Result registerCount(@PathVariable String day);
}