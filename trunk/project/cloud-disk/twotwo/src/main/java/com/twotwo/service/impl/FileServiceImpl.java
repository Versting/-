package com.twotwo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.twotwo.entity.File;
import com.twotwo.mapper.FileMapper;
import com.twotwo.service.FileService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 网盘文件 服务实现类
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {
    @Override
    public List<File> getByDeFile(String company) {
        QueryWrapper<File> wrapper = new QueryWrapper<>();
        wrapper.eq("directory_id", company).eq("is_deleted",1);
        return this.list(wrapper);
    }
    @Override
    public List<File> getListFile() {
        QueryWrapper<File> wrapper = new QueryWrapper<>();
        wrapper.eq("is_deleted",1);
        return this.list(wrapper);
    }
    @Override
    public List<File> getFile(String company) {
        QueryWrapper<File> wrapper = new QueryWrapper<>();
        if(!company.equals("0"))
        {
            wrapper.eq("is_deleted",0).eq("directory_id",company);
        }
        else
            wrapper.eq("is_deleted",0);
        return this.list(wrapper);
    }

}