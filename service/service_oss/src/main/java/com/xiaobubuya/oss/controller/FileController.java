package com.xiaobubuya.oss.controller;

import com.xiaobubuya.oss.service.FileService;
import com.xiaobubuya.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags="阿里云文件管理")
@CrossOrigin
@RestController
@RequestMapping("/oss/file/")
public class FileController {

	@Autowired
	private FileService fileService;

	/**
	 * 头像上传
	 *
	 * @param file
	 */
	@ApiOperation(value = "头像上传")
	@PostMapping("upload")
	public Result uploadAvatar(
			@ApiParam(name = "file", value = "文件", required = true)
			@RequestParam("file") MultipartFile file,
			@ApiParam(name = "folder", value = "文件上传路径", required = false) String folder) {

		String uploadUrl;
		if("cover".equals(folder)){
			uploadUrl = fileService.uploadAvatar(file,"cover");
		}
		else{
			uploadUrl = fileService.uploadAvatar(file,"avatar");
		}
		//返回r对象
		return Result.ok().message("文件上传成功").data("url", uploadUrl);

	}
}