package com.xiaobubuya.order.service.impl;

import com.xiaobubuya.order.client.EduClient;
import com.xiaobubuya.order.client.UcenterClient;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaobubuya.order.entity.TOrder;
import com.xiaobubuya.order.mapper.TOrderMapper;
import com.xiaobubuya.order.service.TOrderService;
import com.xiaobubuya.order.utils.OrderNoUtil;
import com.xiaobubuya.servicebase.vo.CourseInfoCommon;
import com.xiaobubuya.servicebase.vo.UcenterMemberCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author xiaobubuya
 * @since 2021-06-25
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {

    @Autowired
    private EduClient eduClient;

    @Autowired
    private UcenterClient ucenterClient;

    //创建订单
    @Override
    public String saveOrder(String courseId, String memberId) {
        //远程调用用户服务，根据用户id获取用户信息
        UcenterMemberCommon ucenterMember = ucenterClient.getInfo(memberId);

        //远程调用课程服务，根据课程id获取课程信息
        CourseInfoCommon courseDto = eduClient.getCourseInfoDto(courseId);



        //创建订单
        TOrder order = new TOrder();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseDto.getTitle());
        order.setCourseCover(courseDto.getCover());
        order.setTeacherName(courseDto.getTeacherName());
        order.setTotalFee(courseDto.getPrice());
        order.setMemberId(memberId);
        order.setMobile(ucenterMember.getMobile());
        order.setNickname(ucenterMember.getNickname());
        order.setStatus(0);
        order.setPayType(1);
        baseMapper.insert(order);

        return order.getOrderNo();
    }
}
