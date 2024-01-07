package com.twotwo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.twotwo.entity.Operation;

import java.util.List;

/**
 * 操作日志 服务类
 */
public interface OperationService extends IService<Operation> {
    List<Operation> getByDepartment(String companyid);
}