package com.twotwo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.twotwo.entity.File;

import java.util.List;

/**
 * 网盘文件 服务类
 */
public interface FileService extends IService<File> {
    List<File> getByDeFile(String company);
    List<File> getListFile();
    List<File> getFile(String company);
}