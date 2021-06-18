package com.xiaobubuya.oss.service.Impl;

import com.aliyun.oss.OSS;

import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.xiaobubuya.oss.service.FileService;
import com.xiaobubuya.oss.utils.ConstantPropertiesUtil;
import com.xiaobubuya.servicebase.exceptionHandler.GuliException;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadAvatar(MultipartFile file, String folder) {

		//获取阿里云存储相关常量
		String endPoint = ConstantPropertiesUtil.END_POINT;
		String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
		String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
		String bucketName = ConstantPropertiesUtil.BUCKET_NAME;

		String uploadUrl = null;

		try {
			//判断oss实例是否存在：如果不存在则创建，如果存在则获取
			OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
			if (!ossClient.doesBucketExist(bucketName)) {
				//创建bucket
				ossClient.createBucket(bucketName);
				//设置oss实例的访问权限：公共读
				ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
			}

			//获取上传文件流
			InputStream inputStream = file.getInputStream();
			String fileName = UUID.randomUUID().toString().replaceAll("-","")+file.getOriginalFilename();
			//构建日期路径：avatar/2019/02/26/文件名
			String filePath = new DateTime().toString("yyyy/MM/dd");
			String fileUrl = folder + "/" +filePath + "/" + fileName;
			//文件上传至阿里云
			ossClient.putObject(bucketName, fileUrl, inputStream);

			// 关闭OSSClient。
			ossClient.shutdown();

			//获取url地址
			uploadUrl = "https://" + bucketName + "." + endPoint + "/" + fileUrl;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return uploadUrl;
	}
}