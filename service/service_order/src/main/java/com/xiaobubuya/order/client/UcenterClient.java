package com.xiaobubuya.order.client;

import com.xiaobubuya.servicebase.vo.UcenterMemberCommon;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "service-ucenter",fallback = UcenterClientImpl.class)
public interface UcenterClient {
    //根据课程id查询课程信息
    @GetMapping("/ucenter/member/getInfoComment/{memberId}")
    public UcenterMemberCommon getInfo(@PathVariable String memberId);
}