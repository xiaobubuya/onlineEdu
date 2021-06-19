package com.xiaobubuya.vod.service.Impl;

import com.aliyun.oss.ClientException;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.xiaobubuya.servicebase.exceptionHandler.GuliException;
import com.xiaobubuya.vod.service.VideoService;
import com.xiaobubuya.vod.utils.AliyunVodSDKUtils;
import com.xiaobubuya.vod.utils.ConstantPropertiesUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class VideoServiceImpl implements VideoService {

	@Override
	public String uploadVideo(MultipartFile file) {

		try {
			InputStream inputStream = file.getInputStream();
			String originalFilename = file.getOriginalFilename();
			String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));

			UploadStreamRequest request = new UploadStreamRequest(
					ConstantPropertiesUtil.ACCESS_KEY_ID,
					ConstantPropertiesUtil.ACCESS_KEY_SECRET,
					title, originalFilename, inputStream);

			UploadVideoImpl uploader = new UploadVideoImpl();
			UploadStreamResponse response = uploader.uploadStream(request);

			//如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。
			// 其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
			String videoId = response.getVideoId();
			if (!response.isSuccess()) {
				String errorMessage = "阿里云上传错误：" + "code：" + response.getCode() + ", message：" + response.getMessage();
//				log.warn(errorMessage);
				if(StringUtils.isEmpty(videoId)){
					throw new GuliException(20001, errorMessage);
				}
			}

			return videoId;
		} catch (IOException e) {
			throw new GuliException(20001, "guli vod 服务上传失败");
		}
	}

	//	https://help.aliyun.com/document_detail/61065.html?spm=a2c4g.11186623.6.831.654b3815cIxvma#h2--div-id-deletevideo-div-7
	@Override
	public void removeVideo(String videoId){
		try{
			// 初始化对象
			DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(
					ConstantPropertiesUtil.ACCESS_KEY_ID,
					ConstantPropertiesUtil.ACCESS_KEY_SECRET);

			// 创建删除视频request对象
			DeleteVideoRequest request = new DeleteVideoRequest();

			// 向request设置视频id
			request.setVideoIds(videoId);

			// 调用初始化对象的方法实现删除
			DeleteVideoResponse response = client.getAcsResponse(request);

			System.out.print("RequestId = " + response.getRequestId() + "\n");

		}catch (ClientException | com.aliyuncs.exceptions.ClientException e){
			throw new GuliException(20001, "视频删除失败");
		}
	}
}