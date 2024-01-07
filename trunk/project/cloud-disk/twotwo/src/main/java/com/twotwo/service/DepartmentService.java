package com.twotwo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.twotwo.entity.Department;

import java.util.List;

/**
 * 部门 服务类
 */
public interface DepartmentService extends IService<Department> {
    List<Department> getBycompanyId(String company);
}