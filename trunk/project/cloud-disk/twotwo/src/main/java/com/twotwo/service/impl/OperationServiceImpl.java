package com.twotwo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.twotwo.entity.Operation;
import com.twotwo.mapper.OperationMapper;
import com.twotwo.service.OperationService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 操作日志 服务实现类
 */
@Service
public class OperationServiceImpl extends ServiceImpl<OperationMapper, Operation> implements OperationService {


    @Override
    public List<Operation> getByDepartment(String companyid) {
        QueryWrapper<Operation> wrapper = new QueryWrapper<>();
        wrapper.eq("department_id", companyid);
        return this.list(wrapper);
    }
}