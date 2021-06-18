package com.xiaobubuya.edu.controller;


import com.xiaobubuya.edu.entity.vo.CourseInfoVo;
import com.xiaobubuya.edu.service.CourseService;
import com.xiaobubuya.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author xiaobubuya
 * @since 2021-06-17
 */
@Api(tags="课程管理")
@CrossOrigin
@RestController
@RequestMapping("/edu/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @ApiOperation(value = "新增课程")
    @PostMapping("save-course-info")
    public Result saveCourseInfo(
            @ApiParam(name = "CourseInfoForm", value = "课程基本信息", required = true)
            @RequestBody CourseInfoVo courseInfoVo) {

        String courseId = courseService.saveCourseInfo(courseInfoVo);
        if (!StringUtils.isEmpty(courseId)) {
            return Result.ok().data("courseId", courseId);
        } else {
            return Result.error().message("保存失败");
        }
    }

    @ApiOperation(value = "根据ID查询课程")
    @GetMapping("course-info/{id}")
    public Result getById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id){

        CourseInfoVo courseInfoVo = courseService.getCourseInfoFormById(id);
        return Result.ok().data("item", courseInfoVo);
    }

    @ApiOperation(value = "更新课程")
    @PutMapping("update-course-info/{id}")
    public Result updateCourseInfoById(
            @ApiParam(name = "CourseInfoForm", value = "课程基本信息", required = true)
            @RequestBody CourseInfoVo courseInfoVo,

            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id){

        courseService.updateCourseInfoById(courseInfoVo);
        return Result.ok();
    }
}

