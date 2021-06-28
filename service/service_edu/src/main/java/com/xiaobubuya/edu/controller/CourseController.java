package com.xiaobubuya.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaobubuya.edu.client.OrderClient;
import com.xiaobubuya.edu.entity.Course;
import com.xiaobubuya.edu.entity.chapter.ChapterVo;
import com.xiaobubuya.edu.entity.course.CourseQuery;
import com.xiaobubuya.edu.entity.front.CourseWebVo;
import com.xiaobubuya.edu.entity.video.CoursePublishVo;
import com.xiaobubuya.edu.entity.vo.CourseInfoVo;
import com.xiaobubuya.edu.service.ChapterService;
import com.xiaobubuya.edu.service.CourseService;
import com.xiaobubuya.edu.service.TeacherService;
import com.xiaobubuya.utils.JwtUtils;
import com.xiaobubuya.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author xiaobubuya
 * @since 2021-06-17
 */
@Api(tags="课程管理")
@RestController
@RequestMapping("/edu/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private OrderClient orderClient;

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

    @ApiOperation(value = "根据ID获取课程发布信息")
    @GetMapping("course-publish-info/{id}")
    public Result getCoursePublishVoById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id){

        CoursePublishVo courseInfoForm = courseService.getCoursePublishVoById(id);
        return Result.ok().data("item", courseInfoForm);
    }

    @ApiOperation(value = "根据id发布课程")
    @PutMapping("publish-course/{id}")
    public Result publishCourseById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id){
        return courseService.publishCourseById(id)?Result.ok().message("发布成功！"):Result.error().message("发布失败！");
    }

    @ApiOperation(value = "分页课程列表")
    @GetMapping("{page}/{limit}")
    public Result pageQuery(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
                    CourseQuery courseQuery){

        Page<Course> pageParam = new Page<>(page, limit);

        courseService.pageQuery(pageParam, courseQuery);
        List<Course> records = pageParam.getRecords();

        long total = pageParam.getTotal();

        return  Result.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "根据ID删除课程")
    @DeleteMapping("{id}")
    public Result removeById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id){

        boolean result = courseService.removeCourseById(id);
        if(result){
            return Result.ok();
        }else{
            return Result.error().message("删除失败");
        }
    }

    @Autowired
    private ChapterService chapterService;

    @ApiOperation(value = "前台页面展示根据ID查询课程")
    @GetMapping(value = "getCourseFront/{courseId}")
    public Result getCourseFront(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable String courseId, HttpServletRequest request){

        //查询课程信息和讲师信息
        CourseWebVo courseWebVo = courseService.selectInfoWebById(courseId);

        //查询当前课程的章节信息
        List<ChapterVo> chapterVoList = chapterService.nestedList(courseId);

        //远程调用，判断课程是否被购买
        boolean buyCourse = orderClient.isBuyCourse(JwtUtils.getMemberIdByJwtToken(request), courseId);

        return Result.ok().data("course", courseWebVo).data("chapterVoList", chapterVoList).data("isbuyCourse",buyCourse);
//        return Result.ok().data("course", courseWebVo).data("chapterVoList", chapterVoList);
    }

    //根据课程id查询课程信息
    @GetMapping("getCourse/{courseId}")
    public com.xiaobubuya.servicebase.vo.CourseInfoCommon getCourseInfoDto(@PathVariable String courseId) {
        Course course = courseService.getById(courseId);
        String teacherName = teacherService.getById(course.getTeacherId()).getName();
        com.xiaobubuya.servicebase.vo.CourseInfoCommon courseInfo = new com.xiaobubuya.servicebase.vo.CourseInfoCommon();
        BeanUtils.copyProperties(course,courseInfo);
        courseInfo.setTeacherName(teacherName);
        return courseInfo;
    }

    @GetMapping("updateBuyNum/{courseId}")
    public Result updateBuyNum(@PathVariable String courseId){
        Course course = courseService.getById(courseId);
        course.setBuyCount(course.getBuyCount() + 1);
        courseService.updateById(course);
        return Result.ok();
    }

}

