package com.xiaobubuya.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaobubuya.edu.entity.Chapter;
import com.xiaobubuya.edu.entity.Video;
import com.xiaobubuya.edu.entity.chapter.ChapterVo;
import com.xiaobubuya.edu.entity.chapter.VideoVo;
import com.xiaobubuya.edu.mapper.ChapterMapper;
import com.xiaobubuya.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaobubuya.edu.service.VideoService;
import com.xiaobubuya.servicebase.exceptionHandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author xiaobubuya
 * @since 2021-06-17
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private VideoService videoService;

    @Override
    public List<ChapterVo> nestedList(String courseId) {

        //最终要的到的数据列表
        ArrayList<ChapterVo> chapterVoArrayList = new ArrayList<>();

        //获取章节信息
        QueryWrapper<Chapter> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("course_id", courseId);
        queryWrapper1.orderByAsc("sort", "id");
        List<Chapter> chapters = baseMapper.selectList(queryWrapper1);

        //获取课时信息
        QueryWrapper<Video> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("course_id", courseId);
        queryWrapper2.orderByAsc("sort", "id");
        List<Video> videos = videoService.list(queryWrapper2);

        //填充章节vo数据
        int count1 = chapters.size();
        for (int i = 0; i < count1; i++) {
            Chapter chapter = chapters.get(i);

            //创建章节vo对象
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);
            chapterVoArrayList.add(chapterVo);

            //填充课时vo数据
            ArrayList<VideoVo> videoVoArrayList = new ArrayList<>();
            int count2 = videos.size();
            for (int j = 0; j < count2; j++) {

                Video video = videos.get(j);
                if(chapter.getId().equals(video.getChapterId())){

                    //创建课时vo对象
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video, videoVo);
                    videoVoArrayList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVoArrayList);
        }

        return chapterVoArrayList;
    }

    @Override
    public boolean removeChapterById(String id) {

        //根据id查询是否存在视频，如果有则提示用户尚有子节点
        if(videoService.getCountByChapterId(id)){
            throw new GuliException(20001,"该分章节下存在视频课程，请先删除视频课程");
        }

        Integer result = baseMapper.deleteById(id);
        return null != result && result > 0;
    }
}
