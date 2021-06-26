package com.xiaobubuya.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaobubuya.order.entity.TOrder;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author xiaobubuya
 * @since 2021-06-25
 */
public interface TOrderService extends IService<TOrder> {

    String saveOrder(String courseId, String memberIdByJwtToken);
}
