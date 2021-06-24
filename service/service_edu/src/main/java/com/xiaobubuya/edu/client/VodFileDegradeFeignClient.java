package com.xiaobubuya.edu.client;

import com.xiaobubuya.utils.Result;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public Result removeVideo(String videoId) {
        return Result.error().message("删除视频出错了");
    }

    @Override
    public Result removeVideoList(List videoIdList) {
        return Result.error().message("删除多个视频出错了");
    }
}