package com.xiaobubuya.edu.controller;


import com.xiaobubuya.edu.entity.Chapter;
import com.xiaobubuya.edu.entity.chapter.ChapterVo;
import com.xiaobubuya.edu.service.ChapterService;
import com.xiaobubuya.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author xiaobubuya
 * @since 2021-06-17
 */
@Api(tags="课程章节管理")
@RestController
@RequestMapping("/edu/chapter")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    @ApiOperation(value = "嵌套章节数据列表")
    @GetMapping("nested-list/{courseId}")
    public Result nestedListByCourseId(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable String courseId){

        List<ChapterVo> chapterVoList = chapterService.nestedList(courseId);
        return Result.ok().data("items", chapterVoList);
    }

    @ApiOperation(value = "新增章节")
    @PostMapping("addChapter")
    public Result save(
            @ApiParam(name = "chapterVo", value = "章节对象", required = true)
            @RequestBody Chapter chapter){

        chapterService.save(chapter);
        return Result.ok();
    }

    @ApiOperation(value = "根据ID查询章节")
    @GetMapping("findChapter/{id}")
    public Result getById(
            @ApiParam(name = "id", value = "章节ID", required = true)
            @PathVariable String id){

        Chapter chapter = chapterService.getById(id);
        return Result.ok().data("item", chapter);
    }

    @ApiOperation(value = "根据ID修改章节")
    @PutMapping("updateChapter/{id}")
    public Result updateById(
            @ApiParam(name = "id", value = "章节ID", required = true)
            @PathVariable String id,

            @ApiParam(name = "chapter", value = "章节对象", required = true)
            @RequestBody Chapter chapter){

        chapter.setId(id);
        chapterService.updateById(chapter);
        return Result.ok();
    }

    @ApiOperation(value = "根据ID删除章节")
    @DeleteMapping("deleteChapter/{id}")
    public Result removeById(
            @ApiParam(name = "id", value = "章节ID", required = true)
            @PathVariable String id){

        boolean result = chapterService.removeChapterById(id);
        if(result){
            return Result.ok();
        }else{
            return Result.error().message("删除失败");
        }
    }
}

