package com.twotwo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.twotwo.entity.Company;
import com.twotwo.mapper.CompanyMapper;
import com.twotwo.service.CompanyService;
import org.springframework.stereotype.Service;

/**
 * 公司 服务实现类
 */
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {
}