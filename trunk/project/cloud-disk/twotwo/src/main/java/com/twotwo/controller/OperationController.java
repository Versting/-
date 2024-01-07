package com.twotwo.controller;

import com.twotwo.common.R;
import com.twotwo.entity.Log;
import com.twotwo.entity.Operation;
import com.twotwo.service.OperationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/operation")
@Api(tags = "管理操作日志接口")
public class OperationController {
    @Resource
    private OperationService operationService;

    @ApiOperation("查询部门全部操作日志")
    @GetMapping("/list/{id}")
    public R<List<Operation>> list(@PathVariable String id) {
        if(!id.equals("0")) {
            List<Operation> operations = operationService.getByDepartment(id);
            for (int i = 0; i < operations.size(); i++) {
                Operation operation = operations.get(i);
                // 修改操作的某个属性的值
                operation.setDepartment_id(Integer.valueOf(id));
            }
            return R.success(operations);
        }
        else{
            return R.success(operationService.list());
        }



    }

    @PostMapping
    @ApiOperation("添加日志")
    public R<String> save(@RequestBody Operation operation) {
        if (operationService.save(operation)) {
            return R.success();
        }
        return R.error("添加失败!");
    }

}