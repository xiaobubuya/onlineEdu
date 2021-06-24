package com.xiaobubuya.edu.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaobubuya.edu.entity.Course;
import com.xiaobubuya.edu.entity.front.CourseQueryVo;
import com.xiaobubuya.edu.service.CourseService;
import com.xiaobubuya.utils.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: xiaobubuya
 * @Description:
 * @Date Created in 2021-06-24 10:29
 * @Modified By:
 */
@RestController
@RequestMapping("/edu/courseFront")
@CrossOrigin
public class CourseFrontController {
    @Autowired
    private CourseService courseService;

    @ApiOperation(value = "分页课程列表")
    @PostMapping(value = "getCourseFrontList/{page}/{limit}")
    public Result pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
            @RequestBody(required = false) CourseQueryVo courseQuery){
        Page<Course> pageParam = new Page<Course>(page, limit);
        Map<String, Object> map = courseService.pageListWeb(pageParam, courseQuery);
        return Result.ok().data(map);
    }
}
