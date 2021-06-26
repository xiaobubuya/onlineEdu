package com.xiaobubuya.order.client;

import com.xiaobubuya.servicebase.vo.CourseInfoCommon;
import com.xiaobubuya.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "service-edu")
@Service
public interface EduClient {
    //根据课程id查询课程信息
    @GetMapping("/edu/course/getCourse/{courseId}")
    CourseInfoCommon getCourseInfoDto(@PathVariable("courseId") String courseId);

    // 更新购买数
    @GetMapping("/edu/course/updateBuyNum/{courseId}")
    Result updateBuyNum(@PathVariable("courseId") String courseId);
}