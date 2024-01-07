package com.twotwo.controller;

import com.twotwo.common.R;
import com.twotwo.entity.File;
import com.twotwo.entity.FileSharing;
import com.twotwo.service.FileSharingService;
import com.twotwo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/fileSharing")
@Api(tags = "管理文件分享接口")
public class FileSharingController {
    @Resource
    private UserService userService;
    @Resource
    private FileSharingService fileSharingService;

    @ApiOperation("查询文件分享链接*")
    @GetMapping("/{id}")
    public R<Object> getOne(@PathVariable Integer id) {
        return R.success(userService.buildShareTree(id));
    }

    @PostMapping
    @ApiOperation("添加文件分享")
    public R<String> save(@RequestBody FileSharing fileSharing) {
        if (fileSharingService.save(fileSharing)) {
            return R.success();
        }
        return R.error("添加失败!");
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除文件分享")
    public R<String> delete(@PathVariable String id) {
        if (fileSharingService.removeById(id)) {
            return R.success();
        }
        return R.error("删除失败!");
    }

    @ApiOperation("查询全部文件分享")
    @GetMapping("/list")
    public R<List<FileSharing>> list() {
        return R.success(fileSharingService.list());
    }

}