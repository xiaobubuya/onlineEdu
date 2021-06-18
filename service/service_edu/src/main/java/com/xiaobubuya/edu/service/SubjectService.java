package com.xiaobubuya.edu.service;

import com.xiaobubuya.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaobubuya.edu.entity.vo.SubjectNestedVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author xiaobubuya
 * @since 2021-06-16
 */
public interface SubjectService extends IService<Subject> {
    // 读取excel插入数据
    void batchImport(MultipartFile file, SubjectService subjectService);

    // 课程分类列表（树形）
    List<SubjectNestedVo> nestedList();
}
