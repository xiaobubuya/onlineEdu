package com.xiaobubuya.edu.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaobubuya.edu.entity.Teacher;
import com.xiaobubuya.edu.entity.vo.TeacherQuery;
import com.xiaobubuya.edu.service.TeacherService;
import com.xiaobubuya.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-06-15
 */
@RestController
@Api(tags = "讲师管理")
@RequestMapping("/edu/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    /**
     * 1、查询所有讲师
     * @return
     */
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public Result list(){
        List<Teacher> list = teacherService.list(null);
        return Result.ok().data("items",list);
    }

    /**
     * 2、讲师逻辑删除
     * @param id
     * @return
     */
    @ApiOperation(value = "讲师逻辑删除")
    @DeleteMapping("deleteTeacher/{id}")
    public Result removeById(@ApiParam(name = "id",value = "讲师ID",required = true) @PathVariable String id){
        boolean flag = teacherService.removeById(id);
        return flag ? Result.ok(): Result.error();
    }


    /**
     * 3、条件分页查询
     * @param page
     * @param limit
     * @param teacherQuery
     * @return
     */
    @ApiOperation(value = "分页讲师列表")
    @PostMapping("pageTeacherCondition/{page}/{limit}")
    public Result pageQuery(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "teacherQuery", value = "查询对象", required = false)
                    @RequestBody TeacherQuery teacherQuery){

        Page<Teacher> pageParam = new Page<>(page, limit);

        teacherService.pageQuery(pageParam, teacherQuery);
        List<Teacher> records = pageParam.getRecords();
        long total = pageParam.getTotal();

        return  Result.ok().data("total", total).data("rows", records);
    }


    /**
     * 4、新增讲师
     * @param teacher
     * @return
     */
    @ApiOperation(value = "新增讲师")
    @PostMapping("/addTeacher")
    public Result save(
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody Teacher teacher){

        return teacherService.save(teacher) ? Result.ok() : Result.error();
    }

    /**
     * 5、根据ID查询讲师
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("findById/{id}")
    public Result getById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id){

        Teacher teacher = teacherService.getById(id);
        return Result.ok().data("item", teacher);
    }

    /**
     * 5、根据ID修改讲师
     * @param id
     * @param teacher
     * @return
     */
    @ApiOperation(value = "根据ID修改讲师")
    @PutMapping("/updateTeacher/{id}")
    public Result updateById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id,

            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody Teacher teacher){

        teacher.setId(id);
        teacherService.updateById(teacher);
        return Result.ok();
    }

}

