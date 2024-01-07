package com.twotwo.service.impl;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.twotwo.common.UserContext;
import com.twotwo.entity.*;
import com.twotwo.mapper.UserMapper;
import com.twotwo.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户 服务实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private FileService fileService;
    @Resource
    private DirectoryService directoryService;
    @Resource
    private DepartmentService departmentService;
    @Resource
    private FileSharingService fileSharingService;

    @Override
    public List<Directory> getOrgDirectory() {
        User user = getById(UserContext.getId());
        Integer role = user.getRole();
        List<Directory> directoryList = new ArrayList<>();
        // 普通用户
        if (role == 1) {
            directoryList = directoryService.lambdaQuery().eq(Directory::getDepartmentId, user.getDepartmentId()).list();
        }
        // 部门主管
        else if (role == 2) {
            directoryList = directoryService.lambdaQuery().eq(Directory::getDepartmentId, user.getDepartmentId()).list();
        }
        // CEO
        else if (role == 3) {
            // 公司ID
            Integer companyId = user.getCompanyId();
            List<Integer> ids = departmentService.lambdaQuery().eq(Department::getCompanyId, companyId).list()
                    .stream().map(Department::getId).collect(Collectors.toList());
            directoryList = directoryService.lambdaQuery().in(Directory::getDepartmentId, ids).list();
        }
        return directoryList;
    }

    @Override
    public List<Tree<Integer>> buildTree(List<Directory> directoryList) {
        List<TreeNode<Integer>> treeNodes = directoryList.stream()
                .map(e -> {
                    TreeNode<Integer> treeNode = new TreeNode<>(e.getId(), e.getParentId(), e.getName(), 1);
                    Map<String, Object> map = new HashMap<>();
                    List<File> files = fileService.lambdaQuery().eq(File::getIsDeleted, 0).eq(File::getDirectoryId, e.getId()).list();
                    map.put("files", files);
                    treeNode.setExtra(map);
                    return treeNode;
                })
                .collect(Collectors.toList());
        return TreeUtil.build(treeNodes, null);
    }

    @Override
    public List<Tree<Integer>> buildShareTree(Integer shareId) {
        FileSharing fileSharing = fileSharingService.getById(shareId);

        List<Directory> directoryList = directoryService.listByIds(fileSharing.getFileIdList());
        List<TreeNode<Integer>> treeNodes = directoryList.stream()
                .map(e -> {
                    TreeNode<Integer> treeNode = new TreeNode<>(e.getId(), e.getParentId(), e.getName(), 1);
                    Map<String, Object> map = new HashMap<>();
                    List<File> files = fileService.lambdaQuery().eq(File::getIsDeleted, 0).eq(File::getDirectoryId, e.getId()).list();
                    map.put("files", files);
                    treeNode.setExtra(map);
                    return treeNode;
                })
                .collect(Collectors.toList());

        // 创建一个临时目录
        TreeNode<Integer> root = new TreeNode<>(-1, null, getById(fileSharing.getUserId()).getUsername() + "分享的目录", 1);
        List<File> files = fileService.listByIds(fileSharing.getFileIdList());
        root.setExtra(MapUtil.of("files", files));
        treeNodes.add(root);
        return TreeUtil.build(treeNodes, -1);
    }






    @Override
    public List<User> getByCompany(String company) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("company_id", company);
        return this.list(wrapper);
    }
}