package com.twotwo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.twotwo.entity.Directory;
import com.twotwo.mapper.DirectoryMapper;
import com.twotwo.service.DirectoryService;
import org.springframework.stereotype.Service;

/**
 * 网盘目录 服务实现类
 */
@Service
public class DirectoryServiceImpl extends ServiceImpl<DirectoryMapper, Directory> implements DirectoryService {
}