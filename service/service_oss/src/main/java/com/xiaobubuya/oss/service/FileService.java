package com.xiaobubuya.oss.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileService {

	/**
	 * 文件上传至阿里云
	 * @param file
	 * @return
	 */
	String uploadAvatar(MultipartFile file, String folder);
}