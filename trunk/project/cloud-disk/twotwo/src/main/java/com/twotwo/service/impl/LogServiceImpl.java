package com.twotwo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.twotwo.entity.Log;
import com.twotwo.mapper.LogMapper;
import com.twotwo.service.LogService;
import org.springframework.stereotype.Service;

/**
 * 日志 服务实现类
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {
}