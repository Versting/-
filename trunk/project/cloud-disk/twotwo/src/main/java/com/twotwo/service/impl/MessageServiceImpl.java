package com.twotwo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.twotwo.entity.Message;
import com.twotwo.mapper.MessageMapper;
import com.twotwo.service.MessageService;
import org.springframework.stereotype.Service;

/**
 * 消息通知 服务实现类
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {
}