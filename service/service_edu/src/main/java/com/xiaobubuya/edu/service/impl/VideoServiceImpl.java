package com.xiaobubuya.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaobubuya.edu.client.VodClient;
import com.xiaobubuya.edu.entity.Video;
import com.xiaobubuya.edu.entity.video.VideoInfoVo;
import com.xiaobubuya.edu.mapper.VideoMapper;
import com.xiaobubuya.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaobubuya.servicebase.exceptionHandler.GuliException;
import com.xiaobubuya.utils.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author xiaobubuya
 * @since 2021-06-17
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {
    @Autowired
    private VodClient vodClient;

    @Override
    public boolean getCountByChapterId(String chapterId) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id", chapterId);
        Integer count = baseMapper.selectCount(queryWrapper);
        return null != count && count > 0;
    }

    @Override
    public void saveVideoInfo(VideoInfoVo videoInfoVo) {
        Video video = new Video();
        BeanUtils.copyProperties(videoInfoVo, video);
        boolean result = this.save(video);

        if(!result){
            throw new GuliException(20001, "课时信息保存失败");
        }
    }

    @Override
    public VideoInfoVo getVideoInfoFormById(String id) {
        //从video表中取数据
        Video video = this.getById(id);
        if(video == null){
            throw new GuliException(20001, "数据不存在");
        }

        //创建videoInfoForm对象
        VideoInfoVo videoInfoForm = new VideoInfoVo();
        BeanUtils.copyProperties(video, videoInfoForm);

        return videoInfoForm;
    }

    @Override
    public void updateVideoInfoById(VideoInfoVo videoInfoForm) {
        //保存课时基本信息
        Video video = new Video();
        BeanUtils.copyProperties(videoInfoForm, video);
        boolean result = this.updateById(video);
        if(!result){
            throw new GuliException(20001, "课时信息保存失败");
        }
    }

    // 删除课时
    @Override
    public boolean removeVideoById(String id) {

        //删除视频资源
        //查询云端视频id
        Video video = baseMapper.selectById(id);
        String videoSourceId = video.getVideoSourceId();
        //删除视频资源
        if(!StringUtils.isEmpty(videoSourceId)){
            Result r = vodClient.removeVideo(videoSourceId);
            if(r.getCode() == 20001){
                throw new GuliException(20001,"删除视频失败，熔断器...");
            }
        }

        // 删除课时
        Integer result = baseMapper.deleteById(id);
        return null != result && result > 0;
    }

    @Override
    public boolean removeByCourseId(String courseId) {
        //根据courseId查询所有的视频id
        QueryWrapper<Video> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        wrapperVideo.select("video_source_id");
        List<Video> eduVideoList = baseMapper.selectList(wrapperVideo);

        // List<Video>变成List<String>
        List<String> videoIds = new ArrayList<>();
        for(int i = 0;i<eduVideoList.size();i++){
            Video video = eduVideoList.get(i);
            String videoSourceId = video.getVideoSourceId();
            // 放到videoIds集合
            if(!StringUtils.isEmpty(videoSourceId)){
                videoIds.add(videoSourceId);
            }
        }

        // 根据多个视频id删除多个视频
        if(videoIds.size()>0){
            vodClient.removeVideoList(videoIds);
        }

        // 删除课时
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        Integer count = baseMapper.delete(queryWrapper);
        return null != count && count > 0;
    }
}
