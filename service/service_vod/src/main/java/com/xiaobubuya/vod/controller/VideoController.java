package com.xiaobubuya.vod.controller;

import com.xiaobubuya.utils.Result;
import com.xiaobubuya.vod.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags="阿里云视频点播微服务")
@CrossOrigin
@RestController
@RequestMapping("/vod/video")
public class VideoController {

	@Autowired
	private VideoService videoService;

	@PostMapping("upload")
	public Result uploadVideo(
			@ApiParam(name = "file", value = "文件", required = true)
			@RequestParam("file") MultipartFile file) throws Exception {

		String videoId = videoService.uploadVideo(file);
		return Result.ok().message("视频上传成功").data("videoId", videoId);
	}

	@DeleteMapping("{videoId}")
	public Result removeVideo(@ApiParam(name = "videoId", value = "云端视频id", required = true)
						 @PathVariable String videoId){

		videoService.removeVideo(videoId);
		return Result.ok().message("视频删除成功");
	}
}