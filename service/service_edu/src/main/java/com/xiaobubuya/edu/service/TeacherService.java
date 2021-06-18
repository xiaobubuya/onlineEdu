package com.xiaobubuya.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaobubuya.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaobubuya.edu.entity.vo.TeacherQuery;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-06-15
 */
@Service
public interface TeacherService extends IService<Teacher> {
    void pageQuery(Page<Teacher> pageParam, TeacherQuery teacherQuery);

}
