package com.xiaobubuya.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaobubuya.order.entity.TOrder;
import com.xiaobubuya.order.service.TOrderService;
import com.xiaobubuya.utils.JwtUtils;
import com.xiaobubuya.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author xiaobubuya
 * @since 2021-06-25
 */
@RestController
@RequestMapping("/order")
@CrossOrigin
public class TOrderController {

    @Autowired
    private TOrderService torderService;

    //根据课程id和用户id创建订单，返回订单id
    @GetMapping("createOrder/{courseId}")
    public Result save(@PathVariable String courseId, HttpServletRequest request) {
        String orderId = torderService.saveOrder(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return Result.ok().data("orderId", orderId);
    }

    @GetMapping("getOrder/{orderId}")
    public Result get(@PathVariable String orderId) {
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        TOrder torder = torderService.getOne(wrapper);
        return Result.ok().data("item", torder);
    }

    @GetMapping("/isBuyCourse/{memberid}/{id}")
    public boolean isBuyCourse(@PathVariable String memberid,
                               @PathVariable String id) {
        //订单状态是1表示支付成功
        int count = torderService.count(new QueryWrapper<TOrder>().eq("member_id", memberid).eq("course_id", id).eq("status", 1));
        if(count>0) {
            return true;
        } else {
            return false;
        }
    }
}

