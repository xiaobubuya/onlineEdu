package com.xiaobubuya.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaobubuya.edu.entity.Course;
import com.xiaobubuya.edu.entity.CourseDescription;
import com.xiaobubuya.edu.entity.Video;
import com.xiaobubuya.edu.entity.course.CourseQuery;
import com.xiaobubuya.edu.entity.video.CoursePublishVo;
import com.xiaobubuya.edu.entity.vo.CourseInfoVo;
import com.xiaobubuya.edu.mapper.CourseMapper;
import com.xiaobubuya.edu.service.ChapterService;
import com.xiaobubuya.edu.service.CourseDescriptionService;
import com.xiaobubuya.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaobubuya.edu.service.VideoService;
import com.xiaobubuya.servicebase.exceptionHandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Autowired
    private VideoService videoService;

    @Autowired
    private ChapterService chapterService;

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

    // 根据ID获取课程发布信息
    @Override
    public CoursePublishVo getCoursePublishVoById(String id) {
        return baseMapper.getCoursePublishVoById(id);
    }

    @Override
    public boolean publishCourseById(String id) {
        Course course = new Course();
        course.setId(id);
        course.setStatus("Normal");
        Integer count = baseMapper.updateById(course);
        return null != count && count > 0;
    }

    // 课程分页查询
    @Override
    public void pageQuery(Page<Course> pageParam, CourseQuery courseQuery) {

        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");

        if (courseQuery == null){
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }

        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();

        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("title", title);
        }

        if (!StringUtils.isEmpty(teacherId) ) {
            queryWrapper.eq("teacher_id", teacherId);
        }

        if (!StringUtils.isEmpty(subjectParentId)) {
            queryWrapper.ge("subject_parent_id", subjectParentId);
        }

        if (!StringUtils.isEmpty(subjectId)) {
            queryWrapper.ge("subject_id", subjectId);
        }

        baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    public boolean removeCourseById(String id) {

        //根据id删除所有视频
        videoService.removeByCourseId(id);

        //根据id删除所有章节
        chapterService.removeByCourseId(id);

        //根据id删除所有课程详情
        courseDescriptionService.removeById(id);

        //删除封面 TODO

        Integer result = baseMapper.deleteById(id);
        return null != result && result > 0;
    }


}
