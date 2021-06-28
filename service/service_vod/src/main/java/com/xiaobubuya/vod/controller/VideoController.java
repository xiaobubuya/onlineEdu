package com.xiaobubuya.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.xiaobubuya.utils.Result;
import com.xiaobubuya.vod.service.VideoService;
import com.xiaobubuya.vod.utils.AliyunVodSDKUtils;
import com.xiaobubuya.vod.utils.ConstantPropertiesUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(tags="阿里云视频点播微服务")
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

	/**
	* @Description: 批量删除视频
	* @Author: xiaobubuya
	* @Date 2021/6/21
	*/
	@DeleteMapping("delete-batch")
	public Result removeVideoList(
			@ApiParam(name = "videoIdList", value = "云端视频id", required = true)
			@RequestParam("videoIdList") List<String> videoIdList){

		videoService.removeVideoList(videoIdList);
		return Result.ok().message("视频删除成功");
	}

	/**
	* @Description: 根据id获取播放凭证
	* @Author: xiaobubuya
	* @Date 2021/6/24
	*/
	@GetMapping("get-play-auth/{videoId}")
	public Result getVideoPlayAuth(@PathVariable("videoId") String videoId) throws Exception {

		//获取阿里云存储相关常量
		String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
		String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;

		//初始化
		DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(accessKeyId, accessKeySecret);

		//请求
		GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
		request.setVideoId(videoId);

		//响应
		GetVideoPlayAuthResponse response = client.getAcsResponse(request);

		//得到播放凭证
		String playAuth = response.getPlayAuth();

		//返回结果
		return Result.ok().message("获取凭证成功").data("playAuth", playAuth);
	}


}