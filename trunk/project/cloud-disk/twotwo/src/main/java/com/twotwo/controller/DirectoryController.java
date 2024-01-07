package com.twotwo.controller;

import cn.hutool.core.util.StrUtil;
import com.twotwo.common.R;
import com.twotwo.entity.Directory;
import com.twotwo.service.DirectoryService;
import com.twotwo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/directory")
@Api(tags = "管理网盘目录接口")
public class DirectoryController {
    @Resource
    private UserService userService;
    @Resource
    private DirectoryService directoryService;

    @GetMapping("/move/{id}/{targetId}")
    @ApiOperation("移动网盘目录")
    public R<String> move(@PathVariable String id, @PathVariable String targetId) {
        directoryService.lambdaUpdate().eq(Directory::getId, id).set(Directory::getParentId, targetId).update();
        return R.success();
    }

    @PostMapping
    @ApiOperation("添加网盘目录")
    public R<String> save(@RequestBody Directory directory) {
        if (directoryService.save(directory)) {
            return R.success();
        }
        return R.error("添加失败!");
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除网盘目录")
    public R<String> delete(@PathVariable String id) {
        if (directoryService.removeById(id)) {
            return R.success();
        }
        return R.error("删除失败!");
    }

    @PutMapping
    @ApiOperation("修改网盘目录")
    public R<String> update(@RequestBody Directory directory) {
        if (directoryService.updateById(directory)) {
            return R.success();
        }
        return R.error("更新失败!");
    }

    /*@ApiOperation("查询全部网盘目录-树形结构")
    @GetMapping("/list")
    public R<Object> list(String name) {
        List<Directory> orgDirectory = userService.getOrgDirectory();
        if (StrUtil.isNotEmpty(name)) {
            orgDirectory = orgDirectory.stream().filter(e -> e.getName().contains(name)).collect(Collectors.toList());
        }
        return R.success(userService.buildTree(orgDirectory));
    }*/


}