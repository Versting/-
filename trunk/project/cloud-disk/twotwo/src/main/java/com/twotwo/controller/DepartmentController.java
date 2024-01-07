package com.twotwo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.twotwo.common.R;
import com.twotwo.entity.Department;
import com.twotwo.entity.Directory;
import com.twotwo.service.DepartmentService;
import com.twotwo.service.DirectoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/department")
@Api(tags = "管理部门接口")
public class DepartmentController {
    @Resource
    private DepartmentService departmentService;


    @ApiOperation("查询某个部门")
    @GetMapping("/{id}")
    public R<Department> getOne(@PathVariable String id) {
        return R.success(departmentService.getById(id));
    }

    @ApiOperation("查询公司全部部门")
    @GetMapping("/list/{id}")
    public R<List<Department>> list(@PathVariable String id) {
        return R.success(departmentService.getBycompanyId(id));
    }




    @PostMapping
    @ApiOperation("添加部门")
    public R<String> save(@RequestBody Department department) {
        if (departmentService.save(department)) {
            // 创建根目录
            Directory directory = new Directory();
            directory.setDepartmentId(department.getId());
            directory.setName(StrUtil.format("[{}]目录", department.getName()));
            return R.success();
        }
        return R.error("添加失败!");
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除部门")
    public R<String> delete(@PathVariable String id) {
        if (departmentService.removeById(id)) {
            return R.success();
        }
        return R.error("删除失败!");
    }

    /*@PutMapping
    @ApiOperation("修改部门")
    public R<String> update(@RequestBody Department department) {
        if (departmentService.updateById(department)) {
            return R.success();
        }
        return R.error("更新失败!");
    }*/



}