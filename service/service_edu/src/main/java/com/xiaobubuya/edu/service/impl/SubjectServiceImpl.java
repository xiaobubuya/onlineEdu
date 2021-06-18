package com.xiaobubuya.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaobubuya.edu.entity.ExcelSubjectData;
import com.xiaobubuya.edu.entity.Subject;
import com.xiaobubuya.edu.entity.vo.SubjectNestedVo;
import com.xiaobubuya.edu.entity.vo.SubjectVo;
import com.xiaobubuya.edu.mapper.SubjectMapper;
import com.xiaobubuya.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaobubuya.edu.utils.SubjectExcelListener;
import com.xiaobubuya.servicebase.exceptionHandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author xiaobubuya
 * @since 2021-06-16
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {
    //添加课程分类
//poi读取excel内容
    @Override
    public void batchImport(MultipartFile file, SubjectService subjectService) {
        try {
            //1 获取文件输入流
            InputStream inputStream = file.getInputStream();

            // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
            EasyExcel.read(inputStream, ExcelSubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch(Exception e) {
            e.printStackTrace();
            throw new GuliException(20002,"添加课程分类失败");
        }
    }

    @Override
    public List<SubjectNestedVo> nestedList() {

        //最终要的到的数据列表
        ArrayList<SubjectNestedVo> subjectNestedVoArrayList = new ArrayList<>();

        //获取一级分类数据记录
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", 0);
        queryWrapper.orderByAsc("sort", "id");
        List<Subject> subjects = baseMapper.selectList(queryWrapper);

        //获取二级分类数据记录
        QueryWrapper<Subject> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.ne("parent_id", 0);
        queryWrapper2.orderByAsc("sort", "id");
        List<Subject> subSubjects = baseMapper.selectList(queryWrapper2);

        //填充一级分类vo数据
        int count = subjects.size();
        for (int i = 0; i < count; i++) {
            Subject subject = subjects.get(i);

            //创建一级类别vo对象
            SubjectNestedVo subjectNestedVo = new SubjectNestedVo();
//            subjectNestedVo.setId(subject.getId());
//            subjectNestedVo.setTitle(subject.getTitle());
            BeanUtils.copyProperties(subject, subjectNestedVo);
            subjectNestedVoArrayList.add(subjectNestedVo);

            //填充二级分类vo数据
            ArrayList<SubjectVo> subjectVoArrayList = new ArrayList<>();
            int count2 = subSubjects.size();
            for (int j = 0; j < count2; j++) {

                Subject subSubject = subSubjects.get(j);
                if(subject.getId().equals(subSubject.getParentId())){

                    //创建二级类别vo对象
                    SubjectVo subjectVo = new SubjectVo();
                    BeanUtils.copyProperties(subSubject, subjectVo);
                    subjectVoArrayList.add(subjectVo);
                }
            }
            subjectNestedVo.setChildren(subjectVoArrayList);
        }


        return subjectNestedVoArrayList;
    }
}
