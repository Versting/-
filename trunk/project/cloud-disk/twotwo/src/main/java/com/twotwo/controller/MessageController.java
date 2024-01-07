package com.twotwo.controller;

import com.twotwo.common.R;
import com.twotwo.common.UserContext;
import com.twotwo.entity.Message;
import com.twotwo.entity.User;
import com.twotwo.service.MessageService;
import com.twotwo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/message")
@Api(tags = "管理消息通知接口")
public class MessageController {
    @Resource
    private MessageService messageService;
    @Resource
    private UserService userService;

    @PostMapping("/send")
    @ApiOperation("发送消息通知")
    public R<String> send(@RequestBody Message message) {
        message.setIsRead(0);
        if (messageService.save(message)) {
            return R.success();
        }
        return R.error("添加失败!");
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除消息通知")
    public R<String> delete(@PathVariable String id) {
        if (messageService.removeById(id)) {
            return R.success();
        }
        return R.error("删除失败!");
    }

    @ApiOperation("阅读某个消息通知")
    @GetMapping("/read/{id}")
    public R<Message> read(@PathVariable String id) {
        Message message = messageService.getById(id);
        message.setIsRead(1);
        messageService.updateById(message);
        return R.success(message);
    }

    @ApiOperation("查询全部单独消息通知")
    @GetMapping("/listSolo")
    public R<List<Message>> listSolo() {
        return R.success(messageService.lambdaQuery().eq(Message::getTargetUserId, UserContext.getId()).list());
    }

    @ApiOperation("查询全部部门消息通知")
    @GetMapping("/listDepartment")
    public R<List<Message>> listDepartment() {
        User user = userService.getById(UserContext.getId());
        return R.success(messageService.lambdaQuery().eq(Message::getTargetDepartmentId, user.getDepartmentId()).list());
    }
}