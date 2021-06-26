package com.xiaobubuya.order.controller;


import com.xiaobubuya.order.client.EduClient;
import com.xiaobubuya.order.service.PayLogService;
import com.xiaobubuya.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author xiaobubuya
 * @since 2021-06-25
 */
@RestController
@RequestMapping("/order/log")
@CrossOrigin
public class PayLogController {

    @Autowired
    private PayLogService payService;

    /**
     * 生成二维码
     *
     * @return
     */
    @GetMapping("/createNative/{orderNo}")
    public Result createNative(@PathVariable String orderNo) {
        Map map = payService.createNative(orderNo);
        return Result.ok().data(map);
    }


    @GetMapping("/queryPayStatus/{orderNo}")
    public Result queryPayStatus(@PathVariable String orderNo) {
        //调用查询接口
        Map<String, String> map = payService.queryPayStatus(orderNo);
        if (map == null) {//出错
            return Result.error().message("支付出错");
        }
        if (map.get("trade_state").equals("SUCCESS")) {//如果成功
            //更改订单状态
            payService.updateOrderStatus(map);
            return Result.ok().message("支付成功");
        }

        return Result.ok().code(25000).message("支付中");
    }
}

