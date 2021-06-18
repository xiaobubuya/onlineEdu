package com.xiaobubuya.edu.service;

import com.xiaobubuya.edu.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaobubuya.edu.entity.video.VideoInfoVo;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author xiaobubuya
 * @since 2021-06-17
 */
public interface VideoService extends IService<Video> {

    boolean getCountByChapterId(String id);

    void saveVideoInfo(VideoInfoVo videoInfoVo);

    VideoInfoVo getVideoInfoFormById(String id);

    void updateVideoInfoById(VideoInfoVo videoInfoForm);

    boolean removeVideoById(String id);
}
