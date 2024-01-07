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
 * 操作日志
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("操作日志")
public class Operation {

    /**
     * 操作日志ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("操作日志ID")
    private Integer id;

    /**
     * 用户ID
     */
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("用户ID")
    private Integer userId;

    /**
     * 部门ID
     */
    @ApiModelProperty("部门ID")
    private Integer department_id;

    /**
     * 操作内容
     */
    @ApiModelProperty("操作内容")
    private String content;

    /**
     * 文件ID
     */
    @ApiModelProperty("文件ID")
    private Integer fileId;

    /**
     * 操作时间
     */
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("操作时间")
    private Date createTime;

}
