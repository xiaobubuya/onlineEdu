package com.xiaobubuya.edu.client;

import com.xiaobubuya.servicebase.vo.UcenterMemberCommon;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "service-ucenter",fallback = UcenterClientImpl.class)
public interface UcenterClient {
    //根据用户id获取用户信息
    @GetMapping("/ucenter/member/getInfoComment/{memberId}")
    UcenterMemberCommon getUcenterInfo(@PathVariable("memberId") String memberId);
}