package com.twotwo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 网盘目录
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("网盘目录")
public class Directory {

    /**
     * 目录ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("目录ID")
    private Integer id;

    /**
     * 部门ID
     */
    @ApiModelProperty("部门ID")
    private Integer departmentId;

    /**
     * 父目录ID
     */
    @ApiModelProperty("父目录ID")
    private Integer parentId;

    /**
     * 目录名
     */
    @ApiModelProperty("目录名")
    private String name;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("创建时间")
    private Date createTime;

}