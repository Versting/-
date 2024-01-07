package com.twotwo.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.extra.mail.MailUtil;
import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.twotwo.client.OssClient;
import com.twotwo.common.R;
import com.twotwo.common.UserContext;
import com.twotwo.entity.File;
import com.twotwo.entity.User;
import com.twotwo.request.RegisterRequest;
import com.twotwo.request.UpdatePasswordRequest;
import com.twotwo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/user")
@Api(tags = "管理用户接口")
public class UserController {
    @Resource
    private com.twotwo.service.UserService userService;
    @Resource
    private OssClient ossClient;
    private Map<String, String> codeMap = new HashMap<>();

    @PostMapping
    @ApiOperation("用户注册")
    public R<String> register(RegisterRequest request, MultipartFile file) throws IOException {

        String email = request.getEmail();
        if (userService.lambdaQuery().eq(User::getEmail, email).count() > 0) {
            return R.error("账号重复!");
        }
        String code = codeMap.get(request.getEmail());
        //String code ="123";
        if (!StrUtil.equals(request.getCode(), code)) {
            return R.error("验证码错误!");
        }

        //User user = BeanUtil.copyProperties(request, User.class);
        User user=new User();
        user.setEmail(request.getEmail());
        user.setPassword(DigestUtil.md5Hex(request.getPassword()));
        user.setUsername("欢迎使用");
        user.setAvatar("");
        user.setDepartmentId(00000);
        user.setCompanyId(00000);
        user.setRole(1);
        user.setStatus(0);
        // 上传头像
//        if (file != null) {
//            String key = ossClient.upload(file);
//            user.setAvatar(key);
//        }
        if (userService.save(user)) {
            return R.success();
        }
        return R.error("添加失败!");
    }

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public R<User> login(@RequestBody User loginRequest, HttpServletRequest request) {
        loginRequest.setPassword(DigestUtil.md5Hex(loginRequest.getPassword()));
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>(loginRequest);
        // 通过审核的用户才能登录
        //wrapper.eq(User::getStatus, 1);
        User user = userService.getOne(wrapper);
        UserContext.setUser(user);
        if (user == null) {
            return R.error("登录失败!账号或密码错误!");
        }
        request.getSession().setAttribute("user", user);
        return R.success(user);
    }

    @PostMapping("/sendEmail")
    @ApiOperation("发送验证码到邮箱")
    public R<String> sendEmail(@RequestBody RegisterRequest request) {
        String email = request.getEmail();
        // 获取6位随机验证码
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int digit = RandomUtil.randomInt(10);
            builder.append(digit);
        }

        MailUtil.sendText(email, "兔兔网盘系统注册验证码", "验证码: " + builder);
        codeMap.put(email, builder.toString());
        return R.success();
    }

    @PostMapping("/setAdmin/{id}")
    @ApiOperation("设置员工为主管")
    public R<String> setAdmin(@PathVariable String id) {
        userService.lambdaUpdate().eq(User::getId, id).set(User::getRole, 2).update();
        return R.success();
    }

    @PostMapping("/resetAdmin/{id}")
    @ApiOperation("取消设置员工为主管")
    public R<String> resetAdmin(@PathVariable String id) {
        userService.lambdaUpdate().eq(User::getId, id).set(User::getRole, 1).update();
        return R.success();
    }
    @DeleteMapping("/{id}")
    @ApiOperation("删除用户")
    public R<String> delete(@PathVariable String id) {
        if (userService.removeById(id)) {
            return R.success();
        }
        return R.error("删除失败!");
    }


    @ApiOperation("当前登录用户信息")
    @GetMapping("/current")
    public R<User> current() {
        return R.success(userService.getById(UserContext.getId()));
    }


    @ApiOperation("用户通过审核")
    @PostMapping("/audit/{id}")
    public R<User> audit(@PathVariable String id) {
        userService.lambdaUpdate().eq(User::getId, id).set(User::getStatus, 1).update();
        return R.success();
    }


    @ApiOperation("查询某个用户")
    @GetMapping("/{id}")
    public R<User> getOne(@PathVariable String id) {
       // System.out.print(id+"\n");
        //System.out.print(R.success(userService.getById(id))+"\n");
        return R.success(userService.getById(id));
    }
    @PutMapping
    @ApiOperation("修改用户")
    public R<String> update(User user,@RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
//        user.setId(1);
//        user.setEmail("15651735194@qq.com");
//        System.out.println("yes");
        // 上传头像
        if (file != null) {
//            System.out.println(file.getName());
            String key = ossClient.upload(file);
            System.out.println(key);
            user.setAvatar(key);
        }
        if (userService.updateById(user)) {
            return R.success();
        }
        return R.error("更新失败!");
    }
    @GetMapping("/preview")
    @ApiOperation("预览网盘文件")
    public void preview(@RequestParam String id, HttpServletResponse response) throws IOException {
        User user = userService.getById(id);
        String mimeType = HttpUtil.getMimeType(user.getAvatar());
        response.setContentType(mimeType);
        byte[] bytes = ossClient.downloadFile(user.getAvatar());
        response.getOutputStream().write(bytes);
        System.out.println(id);
    }
    @ApiOperation("查询公司的所有用户")
    @GetMapping("/list/{id}")
    public R<List<User>> list(@PathVariable String id) {
        return R.success(userService.getByCompany(id));
    }

    @PutMapping("/updatePassword")
    @ApiOperation("修改用户密码")
    public R<String> updatePassword(/*@RequestBody*/ UpdatePasswordRequest request) {//通过
        //System.out.println(request);
        String oldPassword = request.getOldPassword();
        User user = userService.getById(request.getId());
        //User user = userService.getById(1);
        //System.out.println(222);
        System.out.println(user.getPassword());
        System.out.println(DigestUtil.md5Hex(oldPassword));
        if (!DigestUtil.md5Hex(oldPassword).equals(user.getPassword())) {

            return R.error("旧密码输入错误");
        }
        /*System.out.println(1111);
        System.out.println(request.getNewPassword());
        System.out.println(request.getNewPasswordAgain());*/
        String newPassword = request.getNewPassword();
        String newPasswordAgain = request.getNewPasswordAgain();
        if (!newPasswordAgain.equals(newPassword)) {
            return R.error("两次新密码输入不一致");
        }
        user.setPassword(DigestUtil.md5Hex(newPassword));
        userService.updateById(user);
        return R.success();
    }

    /*@
    public R<String> jiami()*/








}