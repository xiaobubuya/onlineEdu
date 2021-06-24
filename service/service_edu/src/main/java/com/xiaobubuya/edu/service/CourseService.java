package com.xiaobubuya.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaobubuya.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaobubuya.edu.entity.course.CourseQuery;
import com.xiaobubuya.edu.entity.front.CourseQueryVo;
import com.xiaobubuya.edu.entity.front.CourseWebVo;
import com.xiaobubuya.edu.entity.video.CoursePublishVo;
import com.xiaobubuya.edu.entity.vo.CourseInfoVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author xiaobubuya
 * @since 2021-06-17
 */
public interface CourseService extends IService<Course> {
    // 保存课程信息form
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    // 根据id查找课程信息
    CourseInfoVo getCourseInfoFormById(String id);

    // 更新课程
    void updateCourseInfoById(CourseInfoVo courseInfoForm);

    // 根据ID获取课程发布信息
    CoursePublishVo getCoursePublishVoById(String id);

    // 根据id发布课程
    boolean publishCourseById(String id);

    void pageQuery(Page<Course> pageParam, CourseQuery courseQuery);

    boolean removeCourseById(String id);

    List<Course> selectByTeacherId(String id);

    Map<String, Object> pageListWeb(Page<Course> pageParam, CourseQueryVo courseQuery);

    /**
     * 获取课程信息
     * @param id
     * @return
     */
    CourseWebVo selectInfoWebById(String id);

    /**
     * 更新课程浏览数
     * @param id
     */
    void updatePageViewCount(String id);
}
