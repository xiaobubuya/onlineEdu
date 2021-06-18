package com.xiaobubuya.edu.service.impl;

import com.xiaobubuya.edu.entity.Course;
import com.xiaobubuya.edu.entity.CourseDescription;
import com.xiaobubuya.edu.entity.vo.CourseInfoVo;
import com.xiaobubuya.edu.mapper.CourseMapper;
import com.xiaobubuya.edu.service.CourseDescriptionService;
import com.xiaobubuya.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaobubuya.servicebase.exceptionHandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author xiaobubuya
 * @since 2021-06-17
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseDescriptionService courseDescriptionService;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoForm) {

        //保存课程基本信息
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoForm, course);
        boolean resultCourseInfo = this.save(course);
        if(!resultCourseInfo){
            throw new GuliException(20001, "课程信息保存失败");
        }

        //保存课程详情信息
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseInfoForm.getDescription());
        // 描述id即课程id
        courseDescription.setId(course.getId());
        boolean resultDescription = courseDescriptionService.save(courseDescription);
        if(!resultDescription){
            throw new GuliException(20001, "课程详情信息保存失败");
        }

        return course.getId();
    }

    @Override
    public CourseInfoVo getCourseInfoFormById(String id) {
        Course course = this.getById(id);
        if(course == null){
            throw new GuliException(20001, "数据不存在");
        }
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(course,courseInfoVo);
        CourseDescription courseDescription = courseDescriptionService.getById(id);
        if(course != null){
            courseInfoVo.setDescription(courseDescription.getDescription());
        }
        return courseInfoVo;
    }

    @Override
    public void updateCourseInfoById(CourseInfoVo courseInfoVo) {
        //修改课程基本信息
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVo, course);
        boolean resultCourseInfo = this.updateById(course);
        if(!resultCourseInfo){
            throw new GuliException(20001, "课程信息保存失败");
        }

        //修改课程详情信息
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescription.setId(course.getId());
        boolean resultDescription = courseDescriptionService.updateById(courseDescription);
        if(!resultDescription){
            throw new GuliException(20001, "课程详情信息保存失败");
        }
    }
}
