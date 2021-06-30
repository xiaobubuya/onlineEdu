package com.xiaobubuya.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaobubuya.edu.entity.Course;
import com.xiaobubuya.edu.entity.Teacher;
import com.xiaobubuya.edu.service.CourseService;
import com.xiaobubuya.edu.service.TeacherService;
import com.xiaobubuya.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: xiaobubuya
 * @Description:
 * @Date Created in 2021-06-21 20:11
 * @Modified By:
 */
@RestController
@RequestMapping("edu/indexFront")
public class IndexFrontController {
    // 查询前八条热门课程，查询前四名名师
    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;

    @GetMapping("index")
    @Cacheable(key = "'selectIndexList'",value = "index")
    public Result index(){
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.last("LIMIT 8");
        List<Course> CourseList = courseService.list(queryWrapper);

        QueryWrapper<Teacher> teacherWrapper = new QueryWrapper<>();
        teacherWrapper.orderByDesc("id");
        teacherWrapper.last("LIMIT 8");
        List<Teacher> teacherList = teacherService.list(teacherWrapper);
        return Result.ok().data("courseList",CourseList).data("teacherList",teacherList);
    }
}
