package com.xiaobubuya.edu.mapper;

import com.xiaobubuya.edu.entity.Video;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaobubuya.edu.entity.video.CoursePublishVo;

/**
 * <p>
 * 课程视频 Mapper 接口
 * </p>
 *
 * @author xiaobubuya
 * @since 2021-06-17
 */
public interface VideoMapper extends BaseMapper<Video> {
    CoursePublishVo selectCoursePublishVoById(String id);
}
