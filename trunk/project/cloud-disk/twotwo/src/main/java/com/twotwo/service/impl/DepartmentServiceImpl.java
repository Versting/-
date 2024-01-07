package com.twotwo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.twotwo.entity.Department;
import com.twotwo.mapper.DepartmentMapper;
import com.twotwo.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 部门 服务实现类
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {
    @Override
    public List<Department> getBycompanyId(String company) {
        QueryWrapper<Department> wrapper = new QueryWrapper<>();
        wrapper.eq("company_id", company);
        return this.list(wrapper);
    }
}