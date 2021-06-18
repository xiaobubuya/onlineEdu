package com.xiaobubuya.edu.service;

import com.xiaobubuya.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaobubuya.edu.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author xiaobubuya
 * @since 2021-06-17
 */
public interface ChapterService extends IService<Chapter> {
    List<ChapterVo> nestedList(String courseId);

    boolean removeChapterById(String id);
}
