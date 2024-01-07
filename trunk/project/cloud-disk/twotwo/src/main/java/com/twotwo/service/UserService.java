package com.twotwo.service;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;
import com.twotwo.entity.Directory;
import com.twotwo.entity.User;

import java.util.List;

/**
 * 用户 服务类
 */
public interface UserService extends IService<User> {
    List<Directory> getOrgDirectory();

    List<Tree<Integer>> buildTree(List<Directory> directoryList);

    List<Tree<Integer>> buildShareTree(Integer shareId);

    List<User> getByCompany(String company);
}