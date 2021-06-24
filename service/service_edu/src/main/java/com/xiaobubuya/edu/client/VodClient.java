package com.xiaobubuya.edu.client;

import com.xiaobubuya.utils.Result;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: xiaobubuya
 * @Description:
 * @Date Created in 2021-06-21 9:02
 * @Modified By:
 */
@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {
    // 使用nacos调用接口删除单个视频方法
    @DeleteMapping("/vod/video/{videoId}")
    Result removeVideo(@PathVariable("videoId") String videoId);

    // 删除多个阿里云视频的方法
    @DeleteMapping("/vod/video/delete-batch")
    Result removeVideoList(@RequestParam("videoIdList") List<String> videoIdList);
}
