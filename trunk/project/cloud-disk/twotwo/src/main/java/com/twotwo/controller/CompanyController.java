package com.twotwo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.twotwo.common.R;
import com.twotwo.entity.Company;
import com.twotwo.service.CompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/company")
@Api(tags = "管理公司接口")
public class CompanyController {
    @Resource
    private CompanyService companyService;

    @PostMapping
    @ApiOperation("添加公司")
    public R<String> save(@RequestBody Company company) {
        if (companyService.save(company)) {
            return R.success();
        }
        return R.error("添加失败!");
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除公司")
    public R<String> delete(@PathVariable String id) {
        if (companyService.removeById(id)) {
            return R.success();
        }
        return R.error("删除失败!");
    }

    @PutMapping
    @ApiOperation("修改公司")
    public R<String> update(@RequestBody Company company) {
        if (companyService.updateById(company)) {
            return R.success();
        }
        return R.error("更新失败!");
    }

    @ApiOperation("查询某个公司")
    @GetMapping("/{id}")
    public R<Company> getOne(@PathVariable String id) {
        return R.success(companyService.getById(id));
    }

    @ApiOperation("查询全部公司")
    @GetMapping("/list")
    public R<List<Company>> list() {
        return R.success(companyService.list());
    }

    @ApiOperation("分页查询公司")
    @GetMapping("/page")
    public R<List<Company>> page(int current, int size, String field, String content) {
        //若字段不为空,模糊查询
        QueryWrapper<Company> wrapper = new QueryWrapper<>();
        if (field != null) {
            wrapper.eq(field, content);
        }
        //数据总数
        int total = companyService.count(wrapper);
        //分页统计
        Page<Company> page = companyService.page(new Page<>(current, size), wrapper);
        return R.success(page.getRecords(), total);
    }

}