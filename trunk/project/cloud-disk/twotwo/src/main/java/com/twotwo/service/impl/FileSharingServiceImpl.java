package com.twotwo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.twotwo.entity.FileSharing;
import com.twotwo.mapper.FileSharingMapper;
import com.twotwo.service.FileSharingService;
import org.springframework.stereotype.Service;

/**
 * 文件分享 服务实现类
 */
@Service
public class FileSharingServiceImpl extends ServiceImpl<FileSharingMapper, FileSharing> implements FileSharingService {
}