package com.xiaobubuya.edu.entity.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SubjectNestedVo {

	private String id;
	private String title;

	//一个一级分类具有多个二级分类
	private List<SubjectVo> children = new ArrayList<>();
}