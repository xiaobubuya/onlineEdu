package com.xiaobubuya.edu.mapper;

import com.xiaobubuya.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaobubuya.edu.entity.front.CourseWebVo;
import com.xiaobubuya.edu.entity.video.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author xiaobubuya
 * @since 2021-06-17
 */
public interface CourseMapper extends BaseMapper<Course> {
    CoursePublishVo getCoursePublishVoById(String id);

    CourseWebVo selectInfoWebById(String courseId);
}
