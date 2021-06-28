package com.xiaobubuya.statistics.controller;


import com.xiaobubuya.statistics.service.DailyService;
import com.xiaobubuya.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author xiaobubuya
 * @since 2021-06-26
 */
@RestController
@RequestMapping("/statistics/daily")
public class DailyController {
    @Autowired
    private DailyService dailyService;

    @PostMapping("{day}")
    public Result createStatisticsByDate(@PathVariable String day) {
        dailyService.createStatisticsByDay(day);
        return Result.ok();
    }

    @GetMapping("show-chart/{begin}/{end}/{type}")
    public Result showChart(@PathVariable String begin,@PathVariable String end,@PathVariable String type){
        Map<String, Object> map = dailyService.getChartData(begin, end, type);
        return Result.ok().data(map);
    }


}

