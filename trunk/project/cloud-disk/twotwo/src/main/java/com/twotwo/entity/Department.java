package com.twotwo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("部门")
public class Department {

    /**
     * 部门ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("部门ID")
    private Integer id;

    /**
     * 部门名称
     */
    @ApiModelProperty("部门名称")
    private String name;

    /**
     * 公司ID
     */
    @ApiModelProperty("公司ID")
    private Integer companyId;
}
