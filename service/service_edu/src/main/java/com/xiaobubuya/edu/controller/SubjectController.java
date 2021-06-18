package com.xiaobubuya.edu.controller;


import com.xiaobubuya.edu.entity.vo.SubjectNestedVo;
import com.xiaobubuya.edu.service.SubjectService;
import com.xiaobubuya.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author xiaobubuya
 * @since 2021-06-16
 */
@Api(tags="课程分类管理")
@CrossOrigin
@RestController
@RequestMapping("/edu/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    //添加课程分类
    @ApiOperation(value = "Excel批量导入")
    @PostMapping("addSubject")
    public Result addSubject(MultipartFile file) {
        //1 获取上传的excel文件 MultipartFile
        //返回错误提示信息
        subjectService.batchImport(file, subjectService);
        //判断返回集合是否为空
        return Result.ok();
    }

    @ApiOperation(value = "嵌套数据列表")
    @GetMapping("treeList")
    public Result nestedList(){

        List<SubjectNestedVo> subjectNestedVoList = subjectService.nestedList();
        return Result.ok().data("items", subjectNestedVoList);
    }
}

