package com.xiaobubuya.edu.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaobubuya.edu.entity.Course;
import com.xiaobubuya.edu.entity.Teacher;
import com.xiaobubuya.edu.service.CourseService;
import com.xiaobubuya.edu.service.TeacherService;
import com.xiaobubuya.utils.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: xiaobubuya
 * @Description:
 * @Date Created in 2021-06-24 8:50
 * @Modified By:
 */
@RestController
@RequestMapping("/edu/teacherFront")
public class FrontTeacherController {
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseService courseService;

    @ApiOperation(value = "分页讲师列表")
    @GetMapping(value = "getTeacherFrontList/{page}/{limit}")
    public Result pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit){

        Page<Teacher> pageParam = new Page<Teacher>(page, limit);

        Map<String, Object> map = teacherService.pageListWeb(pageParam);

        return  Result.ok().data(map);
    }

    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping(value = "getTeacher/{id}")
    public Result getById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id){

        //查询讲师信息
        Teacher teacher = teacherService.getById(id);

        //根据讲师id查询这个讲师的课程列表
        List<Course> courseList = courseService.selectByTeacherId(id);

        return Result.ok().data("teacher", teacher).data("courseList", courseList);
    }
}
