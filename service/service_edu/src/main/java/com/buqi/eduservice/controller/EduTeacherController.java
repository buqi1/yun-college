package com.buqi.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buqi.eduservice.entity.EduTeacher;
import com.buqi.eduservice.entity.vo.TeacherQuery;
import com.buqi.eduservice.service.EduTeacherService;
import com.buqi.handler.GuliException;
import com.buqi.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author buqi
 * @since 2023-04-19
 */

@Api(description="讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
@Slf4j
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @GetMapping("/findAll")
    @ApiOperation(value = "所有讲师列表")
    public R findAll(){
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("item",list);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "根据ID删除讲师")
    public R deleteById(@PathVariable("id") String id){
        boolean result = eduTeacherService.removeById(id);
        if (result) {
            return R.ok();
        }else {
            return R.error();
        }
    }

    //分页查询
    @PostMapping("{page}/{limit}")
    @ApiOperation(value = "分页查询(带着条件)")
    public R pageList(@PathVariable Long page,
                      @PathVariable Long limit,
                      @RequestBody(required = false) TeacherQuery teacherQuery){
        Page<EduTeacher> pageParam = new Page<>(page,limit);

        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(page,limit);

        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        // 多条件组合查询
        // mybatis学过 动态sql
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //判断条件值是否为空，如果不为空拼接条件
        if(!StringUtils.isEmpty(name)) {
            //构建条件
            wrapper.like("name",name);
        }
        if(level != null) {
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create",end);
        }

        wrapper.orderByDesc("gmt_create");

        //调用方法实现条件查询分页
        eduTeacherService.page(pageTeacher,wrapper);

        long total = pageTeacher.getTotal();//总记录数
        List<EduTeacher> records = pageTeacher.getRecords(); //数据list集合
        return R.ok().data("total",total).data("rows",records);
    }

    //添加讲师
    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        if (save) {
            return R.ok();
        }else {
            return R.error();
        }
    }

    /**
     * 修改讲师：
     * 1.根据id将讲师查出来，
     * 2.修改讲师信息
     */

    @GetMapping("/getTeacher/{id}")
    public R getTeacherById(@PathVariable String id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("eduTeacher",eduTeacher);
    }

    @PostMapping("/upTeacher")
    public R upTeacher(@RequestBody EduTeacher eduTeacher){
        boolean update = eduTeacherService.updateById(eduTeacher);
        if (update) {
            return R.ok();
        }else {
            return R.error();
        }
    }

}

