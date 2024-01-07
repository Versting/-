package com.twotwo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.twotwo.common.R;
import com.twotwo.entity.Log;
import com.twotwo.service.LogService;
import com.twotwo.common.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/log")
@Api(tags = "管理日志接口")
public class LogController {
    @Resource
    private LogService logService;

    @PostMapping
    @ApiOperation("添加日志")
    public R<String> save(@RequestBody Log log) {
        if (logService.save(log)) {
            return R.success();
        }
        return R.error("添加失败!");
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除日志")
    public R<String> delete(@PathVariable String id) {
        if (logService.removeById(id)) {
            return R.success();
        }
        return R.error("删除失败!");
    }

    @PutMapping
    @ApiOperation("修改日志")
    public R<String> update(@RequestBody Log log) {
        if (logService.updateById(log)) {
            return R.success();
        }
        return R.error("更新失败!");
    }

    @ApiOperation("查询某个日志")
    @GetMapping("/{id}")
    public R<Log> getOne(@PathVariable String id) {
        return R.success(logService.getById(id));
    }

    @ApiOperation("查询全部日志")
    @GetMapping("/list")
    public R<List<Log>> list() {
        return R.success(logService.list());
    }

    @ApiOperation("分页查询日志")
    @GetMapping("/page")
    public R<List<Log>> page(int current, int size, String field, String content) {
        //若字段不为空,模糊查询
        QueryWrapper<Log> wrapper = new QueryWrapper<>();
        if (field != null) {
            wrapper.eq(field, content);
        }
        //数据总数
        int total = logService.count(wrapper);
        //分页统计
        Page<Log> page = logService.page(new Page<>(current, size), wrapper);
        return R.success(page.getRecords(), total);
    }

}


