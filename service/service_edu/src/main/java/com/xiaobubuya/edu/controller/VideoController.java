package com.xiaobubuya.edu.controller;


import com.xiaobubuya.edu.entity.video.VideoInfoVo;
import com.xiaobubuya.edu.service.VideoService;
import com.xiaobubuya.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author xiaobubuya
 * @since 2021-06-17
 */
@Api(tags="课时管理")
@CrossOrigin //跨域
@RestController
@RequestMapping("/edu/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @ApiOperation(value = "新增课时")
    @PostMapping("save-video-info")
    public Result save(
            @ApiParam(name = "videoForm", value = "课时对象", required = true)
            @RequestBody VideoInfoVo videoInfoVo){

        videoService.saveVideoInfo(videoInfoVo);
        return Result.ok();
    }

    @ApiOperation(value = "根据ID查询课时")
    @GetMapping("video-info/{id}")
    public Result getVideInfoById(
            @ApiParam(name = "id", value = "课时ID", required = true)
            @PathVariable String id){

        VideoInfoVo videoInfoVo = videoService.getVideoInfoFormById(id);
        return Result.ok().data("item", videoInfoVo);
    }

    @ApiOperation(value = "更新课时")
    @PutMapping("update-video-info/{id}")
    public Result updateCourseInfoById(
            @ApiParam(name = "VideoInfoForm", value = "课时基本信息", required = true)
            @RequestBody VideoInfoVo videoInfoForm,

            @ApiParam(name = "id", value = "课时ID", required = true)
            @PathVariable String id){

        videoService.updateVideoInfoById(videoInfoForm);
        return Result.ok();
    }

    @ApiOperation(value = "根据ID删除课时")
    @DeleteMapping("{id}")
    public Result removeById(
            @ApiParam(name = "id", value = "课时ID", required = true)
            @PathVariable String id){

        boolean result = videoService.removeVideoById(id);
        if(result){
            return Result.ok();
        }else{
            return Result.error().message("删除失败");
        }
    }
}

